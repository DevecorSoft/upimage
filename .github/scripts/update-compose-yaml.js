const fs = require('fs')
const YAML = require('js-yaml')

const composeFile = process.argv[2]
const instruction = process.argv[3]

fs.readFile(composeFile, 'utf8', function (err, data) {
  if (err) {
    console.log(err);
    process.exit(1);
  }
  const compose = YAML.load(data)

  eval('compose.' + instruction)

  fs.writeFileSync(composeFile, YAML.dump(compose))
})
