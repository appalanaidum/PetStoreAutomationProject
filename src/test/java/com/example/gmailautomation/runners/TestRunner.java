package com.example.gmailautomation.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/PetStore.feature", 
glue = "com/stepdefinetions", tags = "@Sanity", 
plugin = {"pretty", "junit:target/cucumber.xml", "html:target/cucumber.html", "json:target/cucumber.json" })
public class TestRunner {

}
