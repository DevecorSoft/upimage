const fs = require('fs')
const YAML = require('js-yaml')

const versionFile = process.argv[2]

fs.readFile(versionFile, 'utf8', function (err, data) {
    if (err) {
        console.log(err);
        process.exit(1);
    }
    const version = YAML.load(data).version
    console.log(version)
})