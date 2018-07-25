# Polymer 3 parsing example
Analyze a Polymer 3 template file (js file) and alter it.

Thanks to [Matti](https://github.com/mjvesa) for the research.

## Notes
* Existing code for parsing template and imports works well.
* The template is a plain HTML string that can be fed to JSoup
as has been done previously.
* The JavaScript AST can be reached at parseResult.ast in the example
code.
* The API used to manipulate the AST is in com.google.javascript.rhino.Node
class. The AST is a tree of Nodes.
* The first node in the AST is the module. The imports are children
of that. The example code has a short piece where all the imports
are removed. It seems easiest to remove imports trought the AST
* As for adding new imports, creating the import statements as plain
strings and concatenating them to the front of the document is
simpler than to use the Node API.
* Changing the template content is easiest done trough the Node API.
An example of the is in the the example program.
