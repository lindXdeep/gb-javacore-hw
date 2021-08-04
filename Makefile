SHELL:=/bin/bash -O globstar

.DEFAULT_GOAL := compile-run

clean:
	rm -r out/*

compile:
	javac -sourcepath src -d out/ src/**/*.java

run:
	java -cp out/ App 

compile-run: compile run
