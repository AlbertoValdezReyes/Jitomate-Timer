# Replace this with the path to your JavaFX SDK's lib folder
PATH_TO_FX=/home/albertiwi/Applications/JavaFX/javafx-sdk-23.0.2/lib

# Java source file
SRC=Pomodoro.java

# Compiled class files
CLASSES=Pomodoro.class

# Main class to run
MAIN=Pomodoro

# Compiler and options
JAVAC=javac
JAVA=java
JAVAC_FLAGS=--module-path $(PATH_TO_FX) --add-modules javafx.controls
JAVA_FLAGS=--enable-native-access=javafx.graphics --module-path $(PATH_TO_FX) --add-modules javafx.controls

# Default target
all: $(CLASSES)

# Compile Java source
%.class: %.java
	$(JAVAC) $(JAVAC_FLAGS) $<

# Run the application
run: all
	$(JAVA) $(JAVA_FLAGS) $(MAIN)

# Clean class files
clean:
	rm -f *.class

