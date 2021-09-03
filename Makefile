#Makefile for Assignment2

JAVAC=/usr/bin/javac
JFLAGS = -g
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
DOCDIR=docs

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<
	
CLASSES=Score.class WordDictionary.class WordRecord.class WordPanel.class WordApp.class 
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean: 
	rm $(BINDIR)/*.class

run: $(CLASS_FILE)
	java -cp $(BINDIR) WordApp 6 9 example_dict.txt

