package edu.union.adt.graph.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses
({
    SimpleGraphTests.class,
    GraphTestsUsingEquals.class
    
})
public class GraphTestSuite
{ // no implementation needed; above annotations do the work.
}
