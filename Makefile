SHELL:=/bin/bash -O globstar  			#You need to tell make to use bash, not sh.

.DEFAULT_GOAL := compile-run

compile:
	mvn -pl chat_client compile

run:
	mvn -pl chat_client javafx:run

compile-run: compile run
