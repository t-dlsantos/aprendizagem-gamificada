package com.grupoenzo.aprendizagem_gamificada.bdd;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.grupoenzo.aprendizagem_gamificada.bdd")
@ConfigurationParameter(
  key = PLUGIN_PROPERTY_NAME,
  value = "pretty, summary, json:target/cucumber/cucumber.json, html:target/cucumber/cucumber.html"
)


public class CucumberSuite {}
