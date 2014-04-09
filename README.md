Appian System Tools

* Server Logs:
	- Components:
		- Custom functions:
			- listLogFiles()
				- Description: Lists available server log file names from the file system
				- Inputs: N/A
				- Outputs: Returns a list (String[]) of server file names
			
		- Smart Services:
			- Get Log Files
				- Description: Creates an appian document and copies the log file content from the file system
				- Inputs: 
					- List of log files to generate
					- Destination folder location to save log files in Appian
				- Outputs:
					- The generated document of the server log file

* Process Model Settings
	- Components:
		- Application
			- Process Model Settings
				- Small Application that queries all process models and then allows for bulk updating of the settings
		- Smart Services:
			- Get Process Model Settings
				- Description: Gets the Security, Cleanup, and Alert Settings for every process model the user has access to
				- Inputs:
					- None
				- Outputs
					- List of ProcessModelSettings (CDT)
			- Set Process Model Settings
				- Description: Bulk sets the Security, Cleanup, or Alert Settings for the select process models
				- Inputs:
					- Process Model Ids (not uuids)
					- Security Settings	
						- Update Security (Boolean)
						- Admin Users and Groups
						- Editor Users and Groups
						- Manager Users and Groups
						- Viewer Users and Groups
						- Initiator Users and Groups
						- Deny Users and Groups
					- Cleanup Settings
						- Update Cleanup (Boolean)
						- Cleanup Type
						- Cleanup Delay
					- Alert Settings
						- Update Alerts (Boolean)
						- Is Custom
						- Is Notify Initiator
						- Is Notify Owner
						- Is Notify By Expression
						- Expression
						- Is Notify Users and Groups
						- Users and Groups
				- Outputs:
					- None 
