             ___                           _____       _             _ _
            |_ _|_ __ ___   __ _  __ _  __|_   _|__   / \   ___  ___(_|_)
             | || '_ ` _ \ / _` |/ _` |/ _ \| |/ _ \ / _ \ / __|/ __| | |
             | || | | | | | (_| | (_| |  __/| | (_) / ___ \\__ \ (__| | |
            |___|_| |_| |_|\__,_|\__, |\___||_|\___/_/   \_\___/\___|_|_|
                                 |___/
                     ____                          _
                    / ___|___  _ ____   _____ _ __| |_ ___ _ __
                   | |   / _ \| '_ \ \ / / _ \ '__| __/ _ \ '__|
                   | |__| (_) | | | \ V /  __/ |  | ||  __/ |
                    \____\___/|_| |_|\_/ \___|_|   \__\___|_|


# ImageToAsciiConverter
This is a java tool that converts an image to ascii values with a few other options

# Usage
To use this ASCII converter you can either compile the sources using javac
```bash
javac ImagetoAscii.java
java ImageToAscii <IMAGE_NAME> [OPTIONS]
```

or run the precompiled jar file
```bash
java -jar ImagetoAscii.jar <IMAGE_NAME> [OPTIONS]
```
## OPTIONS
####pixel-detail
	-d detail_value

This option changes the density of pixels displayed. The larger the value the fewer pixels are converted. e.g. a value of 1 will rastorize the full image while a value of 2 will rastorize half the image, skipping every other pixel The default value is 1

####output-file
	-o output_file

When this option is included followed by a name, the program will use the supplied name as the output file. The default value is IMAGE_NAME-ascii-version.txt

####print-to-console
	-p

When this option is supplied it will print to console as well as to an output file. The default value is false

#Examples

```
java ImageToAscii Artorias.png -d 5
```

![Image of Yaktocat] (/Artorias.png)