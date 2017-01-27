package com.reelbook.app;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.ejb.EJBFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.spi.api.StageConfig;
import org.wildfly.swarm.swagger.SwaggerArchive;
import org.wildfly.swarm.undertow.UndertowFraction;

public class Main {
	public static void main(String[] args) throws Exception {
		Swarm swarm = new Swarm();
		StageConfig stageConfig = swarm.stageConfig();

		swarm.fraction(getUndertowFraction());
		swarm.fraction(getLoggingFraction(stageConfig));
		swarm.fraction(getDatasourcesFraction(stageConfig));
		swarm.fraction(getJpaFraction());
		swarm.fraction(EJBFraction.createDefaultFraction());
		swarm.start();

		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
		deployment.addAsLibrary(swarm.createDefaultDeployment());

		SwaggerArchive archive = deployment.as(SwaggerArchive.class);
		archive.setResourcePackages("com.reelbook.rest.endpoint");
		archive.setTitle("Primus Backend Enpoints");

		deployment.addAllDependencies();
		swarm.deploy(deployment);
	}

	private static UndertowFraction getUndertowFraction() {
		UndertowFraction undertowFraction = UndertowFraction.createDefaultFraction();
		undertowFraction.subresources().server("default-server").subresources().httpListener("default")
				.maxPostSize(Long.MAX_VALUE);
				//.maxPostSize(15728640l);
		return undertowFraction;
	}

	private static LoggingFraction getLoggingFraction(StageConfig stageConfig) {
		return LoggingFraction
				.createDefaultLoggingFraction(Level.valueOf(stageConfig.resolve("logger.level").getValue()));
	}

	private static JPAFraction getJpaFraction() {
		return new JPAFraction().defaultDatasource("jboss/datasources/reelbookDS");
	}

	private static DatasourcesFraction getDatasourcesFraction(StageConfig stageConfig) {
		return new DatasourcesFraction().jdbcDriver("org.postgresql", (d) -> {
			d.driverClassName("org.postgresql.Driver");
			d.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
			d.driverModuleName("org.postgresql");
		}).dataSource("reelbookDS", (ds) -> {
			ds.driverName("org.postgresql");
			ds.connectionUrl(stageConfig.resolve("database.connection.url").getValue());
			ds.userName(stageConfig.resolve("database.connection.userName").getValue());
			ds.password(stageConfig.resolve("database.connection.password").getValue());
		});
	}
}