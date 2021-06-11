SHELL:=/bin/bash -O globstar

.DEFAULT_GOAL := compile-run

compile:
	javac -sourcepath src -d out/ src/**/*.java

run:
	java -cp out/ HomeWorkApp 

compile-run: compile run
