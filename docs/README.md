Advanced Gatling for Stress Testing Web Applications
============================================

This project demonstrates using Gatling for Stress Testing Web Applications.

To run the project, right-click on the Engine class and select Run As:
    — When prompted to, type test and press Enter.

Pre-requisite: 
    1) Use Chrome or Edge Developer tools, select the Network tab (Use Fiddler if it's not working as expected)
    2) Click on Fetch/XHR filter
    3) Press the record button 
    4) Enact your test scenario
    5) Stop the recording
    6) Click on the down arrow to save a copy of the har file
    7) Right-click on the Recorder class in this project and select Run As
    8) Reference gatling_script_recorder_config.png for the configuration
    9) Mirror the config and then click on 'No static resources' and click on 'Run'
    10) The output from the previous step will generate a Java class in the src/test/java/computerdatabase package

Notes
-----------------------------------------------------------------------------------------------------------------------
A) Even if we get a 200 status code in the response, we still might have some error on the page or unexpected behavior.
   Hence, we have to check/validate the content.
B) You can search for a substring in your text when you don't know exactly where the text appears.
C) With stress testing, we are usually more focused on testing the performance of the backend service, rather than 
   whether we can just download lots of files. These elements are typically cached on a production service, and just
   downloading them many times isn't really testing the performance of the backend service.
D) Gatling doesn’t execute JavaScript
E) Fundamental classes: ChainBuilder, FeederBuilder<String>
F) // 10% chance to log out
   randomSwitch().on(
   Choice.withWeight(10, exec(
G) // Using repeat with a counter and group("Browse pass #{pass}") labels each iteration as a separate group in the report
   .repeat(3, "pass").on(
     group("Browse pass #{pass}").on(
H) Gathering the volume metrics, and identifying the scenarios is an important aspect of any performance testing project
I) UML sequence diagrams that show the flow of the test are valuable
J) The volume of data in the database should be similar to production, if applicable
K) Test Population in Gatling represents the number of virtual users, and their injection profile
L) Multiple Gatling simulations can be executed both sequentially and in parallel
M) A closed workload model in Gatling is a method of testing an application can support a fixed number of users
N) A Gatling plugin exists for the Jenkins CI tool