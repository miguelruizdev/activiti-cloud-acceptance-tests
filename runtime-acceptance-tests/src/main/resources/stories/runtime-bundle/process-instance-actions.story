Meta:

Narrative:
As a user
I want to perform org.activiti.cloud.acceptance.operations on process instances

Scenario: check all process definitions are present
Given the user is authenticated as testuser
When the user gets the process definitions
Then all the process definitions are present

Scenario: delete a process instance
Given the user is authenticated as testuser
When the user starts a PROCESS_INSTANCE_WITH_VARIABLES
And the user deletes the process
Then the process instance is deleted

Scenario: try activate a cancelled process instance
Given the user is authenticated as testuser
And any suspended process instance
When the user deletes the process
Then the process cannot be activated anymore

Scenario: show a process instance diagram
Given the user is authenticated as testuser
When the user starts a PROCESS_INSTANCE_WITH_VARIABLES
And open the process diagram
Then the diagram is shown

Scenario: show diagram for a process instance without graphic info
Given the user is authenticated as testuser
When the user starts a PROCESS_INSTANCE_WITHOUT_GRAPHIC_INFO
And open the process diagram
Then no diagram is shown

Scenario: complete a process instance that uses a connector
Given the user is authenticated as testuser
When the user starts a CONNECTOR_PROCESS_INSTANCE
Then the status of the process is changed to completed
And a variable was created with name var1

Scenario: retrieve process instances as an admin
Given the user is authenticated as hradmin
When the user starts a PROCESS_INSTANCE_WITH_VARIABLES
Then the user can get process with variables instances in admin endpoint

Scenario: query process instances as an admin
Given the user is authenticated as hradmin
When the user starts a PROCESS_INSTANCE_WITH_VARIABLES
Then the user can query process with variables instances in admin endpoints

Scenario: get events as an admin
Given the user is authenticated as hradmin
When the user starts a PROCESS_INSTANCE_WITH_VARIABLES
Then the user can get events for process with variables instances in admin endpoint

Scenario: check the presence of formKey field in task
Given the user is authenticated as testuser
Then the PROCESS_INSTANCE_WITH_SINGLE_TASK_ASSIGNED definition has the formKey field with value startForm

