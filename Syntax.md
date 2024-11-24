# Syntax expressions
IDENTIFIER = letters,numbers,space
XPATH = any string compatible with xpath
COMMAND = Any reserved word or string defined in a library
reserved word = {Search, Expect, Then, [Arguments]}
library = Browser defines [Click on, Type, ]
parameters = this is defined exactly by the COMMAND
EXPECTATION = any string defined in a library
PROPERTY = Defined in a DataSpec
METHOD = Defined in a DataSpec

# Native Commands
Search > IDENTIFIER > with > IDENTIFIER > equals to > IDENTIFIER
Expect > IDENTIFIER > to be > EXPECTATION
Then > COMMAND > command parameters
Sum > IDENTIFIER > as > IDENTIFIER
Expect to > COMMAND > ...

# Browser Commands
Click on > IDENTIFIER
Type > IDENTIFIER > on > IDENTIFIER
Get Number > IDENTIFIER
Expect > IDENTIFIER > In column > IDENTIFIER > In table > IDENTIFIER

# Mapping
id  xpath   implements
id = IDENTIFIER
xpath = XPATH
implements = IDENTIFIER,IDENTIFIER,IDENTIFIER...

# Method
IDENTIFIER (Test)
    COMMAND parameters
    COMMAND parameters
    COMMAND parameters
    ...

# Test
(Optional section)
Use IDENTIFIER
    IDENTIFIER
    IDENTIFIER
    ...

(method)

(another method)

# Page
(same as Test, just they can use LIBCOMs and Tests can only use PAGECOMs)


If we let libraries define syntax like in cucumber then we can define patterns and we can resolve them easily without having to have a monolitic central code to parse every code, and special cases for "internal" libraries like FE or BE.
So that means all Pages and "Services?" are going to use cucumber-like functions. then tests only use Pages or "Services?"
How does EXPECTATION enter into cucumber-like? Native commands are not special, just cucumber-like phrases that are defined before anybody, right now there is only "to be" that i think that should be "to be equals to", then FE can define "to be displayed". That means there is no limit in the phrases, and no standard, as of internal libraries we define a standard, but for PageComs and ServComs there is no standard but they cannot define complex phrases, only methods, with a name at the beginning and parameters after, the parameters should support named parameters, for readability. Should they be separate cells like NAME=,Veronica? If they are separate then the user can generate cucumber-like phrases, but not complex., which is good. Would there be confusion about PageComms, ServComms and methods? some have "=", some don't. Maybe the difference is good, to diferenciate commands with methods.
If we define cucumber-like phrases then we can define we only accept integer IDENTIFIERs, or dates, which is very good.
Obviously if somebody define two phrases that are the same, then you need FE.phrase, same for methods, Page.Method.
Add IfMobile, IfDesktop


# New features
* If a keyword is empty then the previous keyword is repeated
* Variables are defined with {variable}
* Variables that come from the data spec files are marked with <variable>
* Parameters always use named parameters. really? for modules only or tests only? both?
* A section has parameters on the right to define meta-data


# Expect patterns
## Tables
* to find,{string},in the column,<Column>,of the table,<Table>

## API
* <Status code>, equals to, {integer}
* <Extracted>, equals to,{defined type}

## Browser
* <Element>, equals to, {string}  # Element.getText()
* <Element>, to be, displayed
* <Page>, to be, displayed  # How?

# SEARCH???

# Supposedly
The name of the methods would not matter, but the steps inside matter because those are the test steps.
Although it would be necessary to override this manually in cases where it would not make sense when the method is too
technical.

We can only use methods defined in modules right? what about Sum? that is coming from a library. Definitely we should
be able to use Selenium in a test file.
