# How to Checkstyle (recommendation)
## Installation & setup of the Checkstyle-IDEA plugin


1.In IntelliJ, go to File -> Settings -> Plugins


2.Select "Browse Repositories"


3.Find Checkstyle-IDEA and install it. Restart IntellIJ when done


4.After restarting, go to File -> Settings -> Checkstyle. Under configuration file, click the '+' symbol.


5.Select "Use a local Checkstyle file", choose the "CSE1105.checkstyle.xml" under our project


6.Setup complete!


## Running Checkstyle tests

1.In IntelliJ, at the top select Analyze -> Inspect Code

2.For insepction scope, I recommend using the class you want to test. Alternatively, can be run for the entire project.

3.Run the inspection. At the bottom, you will see the tab "CheckStyle". Select it.

4.Run CheckStyle analysis

5.Fix warnings


### Other
* Make sure to use autoformatting. This will automatically fix (most) of the ChcekStyle formatting issues!
* Assuming IntelliJ, these are the hotkeys:
* For Windows: Ctrl + Alt + L
* For Ubuntu: Ctrl + Alt + windows + L
* For Mac: Command + Option + L