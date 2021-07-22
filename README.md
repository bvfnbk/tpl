# `templr`

[![CodeFactor](https://www.codefactor.io/repository/github/bvfnbk/templr/badge)](https://www.codefactor.io/repository/github/bvfnbk/templr)

---

A command-line client to combine JSON _models_ with _templates_ in order to create files. It embeds the _Freemarker_
template engine and was a result of a quick _Kotlin_ script hack.

## Usage

```bash
Usage: templr [OPTIONS] OUTPUT

  Runs the templr application.

Options:
  -D KEY=VALUE           A key/value pair to be passed to the application.
  -c, --charset CHARSET  The input/output charset to be used. (default: UTF-8)
  -m, --model PATH       The path to the JSON model.
  -t, --template PATH    The path to the freemarker template.
  -h, --help             Show this message and exit
```

Load the _model_ from a JSON file `./model.json` and use the _Freemarker_ template `./template.ftl` to generate
the `./output` file:

```bash
templr --model ./model.json --template ./template.ftl ./output
```

**Please note:**

* The model file must exist and be a regular file.
* The template file must exist and be a regular file.
* The output file may exist and must be a regular file in that case.

## Models

The tool currently only supports JSON models. Create a JSON file containing the properties you want to use in your
template, e.g.

```json
{
  "string" : "string",
  "int" : 1234,
  "float" : 1.234,
  "boolean" : true,
  "list" : [ 1, 2, 3, 4 ],
  "object" : {
    "value" : "..."
  }
}
```

**Please note:** A _list_ is a valid JSON object as well; i.e. you are allowed to specify a model like

```json
[
  {
    "id" : 1,
    "name" : "Name"
  }
]
```

_Freemarker_, unfortunately, does not allow _list_ models, grumbling about "_(...)No `TemplateHashModel`(...)". This is
why a generic _list_ is wrapped into an object using the property name `elementList`, i.e. the previous model
effectively becomes

```json
{
  "elementList" : [
    {
      "id" : 1,
      "name" : "Name"
    }
  ]
}
```

**Please note:** It may actually be possible to use generic lists as models in _Freemarker_ - eventually by using custom
object wrappers or similar. The described "work-around" is acceptable which is why this is not very high priority, hints
are welcome though.

## Resources

* The embedded template engine: https://freemarker.apache.org/
* Unintentionally borrowing some ideas from https://github.com/sgoeschl/freemarker-cli
