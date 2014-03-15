DELETE FROM OCCURRED_EVENT
/

DELETE FROM ORDERED_EVENT
/

DELETE FROM ABSENCE
/

DELETE FROM DEPENDENCY
/

DELETE FROM ACTION_AUDIT
/

DELETE FROM GROUP_HIBERNATE_JUNCTION
/

DELETE FROM DEFINITION_GROUP_JUNCTION
/

DELETE FROM GROUP_HIBERNATE
/

DELETE FROM TASK_COMMENT
/

DELETE FROM ALERT
/

DELETE FROM DEFINITION_MATERIALIZATION
/

DELETE FROM TASK
/

DELETE FROM JOB
/

DELETE FROM TASK_DEFINITION_ALERTS
/

DELETE FROM JOB_DEFINITION_ALERTS
/

DELETE FROM ALERT_DEFINITION_ROLE
/

DELETE FROM ALERT_DEFINITION_USER
/

DELETE FROM ALERT_PLUGIN_DISTRIBUTION
/

DELETE FROM ALERT_DEFINITIONS
/

DELETE FROM WORKFLOW_ELEMENT
/

DELETE FROM WORKFLOW_ELEMENT_GROUP
/

DELETE FROM TASK_DEFINITION
/

DELETE FROM JOB_DEFINITION
/

DELETE FROM JOB_TYPE
/

DELETE FROM WORKFLOW_STATE
/

DELETE FROM DEFAULT_NAVIGATION
/

DELETE FROM RG_NAVIGATION
/

DELETE FROM ABSTRACT_NAVIGATION
/

DELETE FROM PARAMETER
/

DELETE FROM SPECIAL_WORKING_DAY
/

DELETE FROM USUAL_NON_WORKING_DAY
/

DELETE FROM USUAL_NON_WORKING_WEEK_DAY
/

DELETE FROM MONTH_DAY
/

DELETE FROM MANAGER_ROLE
/

DELETE FROM CALENDAR
/

DELETE FROM WORKFLOW
/

DELETE FROM GROUP_COMPLETION
/

DELETE FROM GROUP_COMPLETION_WITH_ERRORS
/

DELETE FROM COMPLETION
/

DELETE FROM COMPLETION_WITH_ERRORS
/

DELETE FROM EXACT_DATE
/

DELETE FROM WEEKLY_EVENT
/

DELETE FROM YEARLY_EVENT
/

DELETE FROM MONTHLY_EVENT
/

DELETE FROM DAILY_EVENT
/

DELETE FROM TIME_CYCLE_BASE_EVENT
/

DELETE FROM RELATIVE_EVENT
/

DELETE FROM EVENT
/

DELETE FROM CUSTOM_EVENT
/

DELETE FROM AUTH_USER_HELP_COMMENT
/

DELETE FROM ENTITY_ATTRIBUTE
/

DELETE FROM ROLE_ATTRIBUTE_ACL
/

DELETE FROM AUTH_USER_GROUP_PREFERENCE
/

DELETE FROM PREDEF_ALERT_DEFINITIONS
WHERE NAME NOT IN ('Task Created', 'Task Done', 'Task Declined', 'Task Reverted',
                   'Task Finished', 'Task Overdue')
/

DELETE FROM STATES
WHERE NAME NOT IN ('FINISHED', 'DONE', 'ACTIVE')
/

DELETE FROM JOB_STATES
WHERE ID > 5
/

DELETE FROM TIME_CYCLE
WHERE ID > 7
/

DELETE FROM ROLE_ENTITY_ACL
WHERE (ROLE_ID != (SELECT
                     ID
                   FROM AUTH_ROLE
                   WHERE NAME = 'Admin') OR ENTITY_ID != (SELECT
                                                            ID
                                                          FROM ENTITY
                                                          WHERE NAME = 'portalApp') OR ACL != 15)
      AND
       (ROLE_ID != (SELECT
                            ID
                          FROM AUTH_ROLE
                          WHERE NAME = 'Guest') OR ENTITY_ID != (SELECT
                                                                   ID
                                                                 FROM ENTITY
                                                                 WHERE NAME = 'portalApp') OR ACL != 1)
/

DELETE FROM ENTITY_ATTRIBUTE_TYPE
WHERE NAME NOT IN ('Integer', 'Long', 'Real', 'String', 'Timestamp', 'Boolean')
/

DELETE FROM DEFINITION_TYPE
WHERE NAME NOT IN ('TASK_DEFINITION', 'JOB_DEFINITION')
/

DELETE FROM ENTITY
WHERE NAME NOT IN ('/', 'com', 'frsglobal', 'portalApp',
                   'usecase', 'atf', 'admin_menu', 'atf_role', 'atf_group', 'atf_user',
                   'atf_rights', 'atf_entity', 'atf_attribute', 'user_menu', 'user_preferences',
                   'change_password', 'help', 'help_use_case_link', 'help_pdf_link',
                   'help_video_link', 'windows', 'copy', 'workflow', 'jobDefinition', 'taskDefinition', 'groupDefinition', 'alertDefinition', 'dataModel',
                   'portal', 'job_logs_use_case', 'job_monitoring_menu', 'job_monitoring_use_case',
                   'calendar_use_case', 'absence_use_case', 'organization_chart_use_case', 'definition_group_use_case',
                   'alert_definitions_use_case', 'job_definition_use_case', 'task_definition_use_case', 'workflow_use_case',
                   'portal', 'workflow_menu', 'technical_menu', 'email_receiver_use_case','administration', 'role', 'group', 'user', 'Instance[Name=Admin]', 'Instance[Name=Guest]',
                   'Instance[Name=Administration]', 'Instance[Name=AdminUser]', 'Instance[Name=GuestUser]', 'Instance[Name=SUPER_USER]')
/

DELETE FROM AUTH_GROUP_ROLE
WHERE GROUP_ID IN (SELECT
                     ID
                   FROM AUTH_GROUP
                   WHERE NAME NOT IN ('Administration', 'Guest'))
/

DELETE FROM AUTH_USER_GROUP
WHERE USER_ID IN (SELECT
                    ID
                  FROM AUTH_USER
                  WHERE NAME NOT IN ('AdminUser', 'GuestUser', 'SUPER_USER'))
/

DELETE FROM AUTH_ROLE
WHERE NAME NOT IN ('Admin', 'Guest')
/

DELETE FROM AUTH_GROUP
WHERE NAME NOT IN ('Administration', 'Guest')
/

-- DELETE FROM USER_PREFERENCE WHERE USER_NAME NOT IN ('AdminUser', 'SUPER_USER', 'GuestUser') //TODO link column?
-- /

DELETE FROM AUTH_USER
WHERE NAME NOT IN ('AdminUser', 'SUPER_USER', 'GuestUser')
/

DELETE FROM SCHEDULE_CHANGE
/