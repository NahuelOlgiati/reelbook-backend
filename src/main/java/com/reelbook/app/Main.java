package com.reelbook.app;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.ejb.EJBFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.swagger.SwaggerArchive;

public class Main {

	public static void main(String[] args) throws Exception {

		Swarm swarm = new Swarm();

		final MainProperties mp = MainProperties.getMe();
		
		swarm.fraction(LoggingFraction.createDebugLoggingFraction());
		swarm.fraction(getDatasourcesFraction());
		swarm.fraction(getJpaFraction());
		swarm.fraction(EJBFraction.createDefaultFraction());
		swarm.start();

		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
		deployment.addAsLibrary(swarm.createDefaultDeployment());

		SwaggerArchive archive = deployment.as(SwaggerArchive.class);
		archive.setResourcePackages(mp.getMsg("swagger_resourcepackages"));
		archive.setTitle(mp.getMsg("swagger_title"));

		deployment.addAllDependencies();
		// deployment.staticContent(); DESCOMENTAR ROMPE TODO LPM
		swarm.deploy(deployment);
	}

	private static JPAFraction getJpaFraction() {
		final MainProperties mp = MainProperties.getMe();
		return new JPAFraction().defaultDatasource(mp.getMsg("jpa_default_datasource"));
	}

	private static DatasourcesFraction getDatasourcesFraction() {
		final MainProperties mp = MainProperties.getMe();
		return new DatasourcesFraction().jdbcDriver(mp.getMsg("driver_child_key"), (d) -> {
			d.driverClassName(mp.getMsg("driver_classname"));
			d.xaDatasourceClass(mp.getMsg("driver_xa_datasourceclass"));
			d.driverModuleName(mp.getMsg("driver_modulename"));
		}).dataSource(mp.getMsg("datasource_child_key"), (ds) -> {
			ds.driverName(mp.getMsg("datasource_drivername"));
			ds.connectionUrl(mp.getMsg("datasource_connection_url"));
			ds.userName(mp.getMsg("datasource_username"));
			ds.password(mp.getMsg("datasource_password"));
		});
	}
}