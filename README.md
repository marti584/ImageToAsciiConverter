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
To use this ASCII converter supply the commands with
```bash
java ImageToAscii <IMAGE_NAME> [[[int pixel_detail] bool print_to_console] String <OUTPUT_FILENAME>]
```
All options in [] are optional and have default values if not supplied
int [pixel_detail]: The higher the pixel_detail, the more pixels are skipped
e.g. a pixel_detail of default=1 will rastorize the entire image
whereas a pixel_detail of 2 will rastorize half the image,
skipping every other pixel
bool [print_to_console]: This is a boolean so it accepts default='true' or 'false' as values
String [OUTPUT_FILENAME]: Supply your own filename to save to. default='<IMAGE_NAME>-ascii-version.txt'