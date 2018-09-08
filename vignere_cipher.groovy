class VigenereCipher {

    private keyArray
    private passPhrase

    static void main(args) {
        def mode = System.console().readLine 'Enter 1 for Encoding, Enter 2 for Decoding'
        def wording

        switch(mode) {
            case "1":
                println ("-------\nEncoding Mode\n-------")
                wording = "Encoded"
                break
            case "2":
                println ("-------\nDecoding Mode\n-------")
                wording = "Decoded"
                break
            default:
                println ("Invalid input, quitting...")
                return
        }

        def plainText = System.console().readLine "Enter Plain Text to be ${wording}: "
        def passPhrase = System.console().readLine 'Enter Pass Phrase: '
        def hasSpaces = plainText.indexOf(" ")

        println ("Plain Text: ${plainText}")

        VigenereCipher cipher = new VigenereCipher(passPhrase)
        def encodedText

        if (mode == "1") {
            encodedText = cipher.encrypt(plainText)
        } else {
            encodedText = cipher.decrypt(plainText)
        }

        if (hasSpaces) {
            char[] encodedChars = encodedText.toCharArray()
            encodedChars[hasSpaces] = ' '
            encodedText = new String(encodedChars)
        }

        println ("${wording} Text: ${encodedText}")
    }

    VigenereCipher(passPhrase) {
        this.passPhrase = passPhrase
        int keylen = passPhrase.length()
        int[] KeyArray = new int[keylen]

        keyArray = stringToIntArray(passPhrase)

        this.keyArray = keyArray
    }

    def encrypt(plainText) {
        int[] encryptArray = stringToIntArray(plainText)
        int[] encryptedLine = new int[plainText.length()]

        for (int j = 0; j < plainText.length(); j++) {
            encryptedLine[j] = (keyArray[j % passPhrase.length()] + encryptArray[j]) % 26;
        }

        plainText = intArraytoString(encryptedLine)
        return plainText
    }

    def decrypt(cipherText) {
        int[] decryptArray = stringToIntArray(cipherText)
        int[] decryptedLine = new int[cipherText.length()]

        for (int k = 0; k < cipherText.length(); k++) {
            decryptedLine[k] = (decryptArray[k] - keyArray[k % passPhrase.length()] + 26) % 26
        }

        cipherText = intArraytoString(decryptedLine)
        return cipherText
    }

    static int[] stringToIntArray(text) {
        try{
            int len = (text.length())
            int[] intArray = new int[len]

            for (int i = 0; i < len; i++){
                intArray[i] = (text.charAt(i) - 97)
            }

            return intArray
        } catch (Exception e) {
            println("Error converting string to int: ${e.getMessage()}")
            return null
        }
    }

    static String intArraytoString(encodedText) {
        int len = (encodedText.length)
        char[] charString = new char[len]

        for (int i = 0; i < len; i++) {
            charString[i] = (char)(encodedText[i] + 97)
        }

        return new String(charString)
    }

}
