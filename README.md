tapestry-compat
===============

A Tapestry library that adds back functionality that has been removed since 5.1

The tapestry compat library adds back some classes and methods from
the Tapestry 5.1 version that were deprecated and removed in later
versions.  This library can help ease the migration cost for projects
that make extensive use of removed code.

Some things that are added back in:

- `@IncludeStylesheet` and `@IncludeJavascriptLibrary`
- `PrimaryKeyEncoder`
- `Defense` util with `notNull`, `notBlank` and `cast`
- `RequestPathOptimizer`

