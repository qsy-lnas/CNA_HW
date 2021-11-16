    Console  console = System.console();
        String str = "UGFzc3dvcmQ6";
        Decoder decoder = Base64.getDecoder();
        byte [] b = decoder.decode(str.getBytes());
        String s = Byte.toString(b);
        console.printf(s);