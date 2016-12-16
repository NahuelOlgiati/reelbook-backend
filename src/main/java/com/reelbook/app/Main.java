package com.reelbook.app;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.ejb.EJBFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.spi.api.StageConfig;
import org.wildfly.swarm.swagger.SwaggerArchive;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		Swarm swarm = new Swarm();

		swarm.fraction(LoggingFraction.createDefaultLoggingFraction());
		swarm.fraction(getDatasourcesFraction(swarm.stageConfig()));
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

	private static JPAFraction getJpaFraction()
	{
		return new JPAFraction().defaultDatasource("jboss/datasources/reelbookDS");
	}

	private static DatasourcesFraction getDatasourcesFraction(StageConfig stageConfig)
	{
		return new DatasourcesFraction().jdbcDriver("org.postgresql", (d) -> {
			d.driverClassName("org.postgresql.Driver");
			d.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
			d.driverModuleName("org.postgresql");
		}).dataSource("reelbookDS", (ds) -> {
			ds.driverName("org.postgresql");
			//ds.connectionUrl(stageConfig.resolve("database.connection.url").getValue());
			//ds.userName(stageConfig.resolve("database.connection.userName").getValue());
			//ds.password(stageConfig.resolve("database.connection.password").getValue());
			ds.connectionUrl("jdbc:postgresql://ec2-54-235-247-224.compute-1.amazonaws.com:5432/d5hskcb50gue2b");
			ds.userName("ccgfaepvdinocb");
			ds.password("b5802b30a700000762d32d79ea123fa98f3bfac28417ef6072487d810ca6d6cf");
		});
	}
}