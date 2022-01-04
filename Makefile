SHELL:=/bin/bash -O globstar

.DEFAULT_GOAL := compile-run

.PHONY: all test clean

compile:
	javac -d ./out -sourcepath ./src src/main/**/*.java

run:
	java -cp .:out/ App

test:
	mkdir -p out/
	find ./src/test/java/* | grep \\.java$ > sources.txt
	javac -d ./out/tests -sourcepath ./src @sources.txt
	java -cp ./out/tests AppTest

compile-run: compile run



