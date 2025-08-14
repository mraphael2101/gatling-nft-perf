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
