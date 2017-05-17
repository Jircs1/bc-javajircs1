package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Hashtable;

import org.bouncycastle.tls.crypto.DHGroup;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.encoders.Hex;

public class TlsDHUtils
{
    static final BigInteger TWO = BigInteger.valueOf(2);

    public static final Integer EXT_negotiated_ff_dhe_groups = Integers.valueOf(ExtensionType.negotiated_ff_dhe_groups);

    /*
     * TODO[draft-ietf-tls-negotiated-ff-dhe-01] Move these groups to DHStandardGroups once reaches RFC
     */
    private static BigInteger fromHex(String hex)
    {
        return new BigInteger(1, Hex.decode(hex));
    }

    private static DHGroup fromSafeP(String hexP)
    {
        BigInteger p = fromHex(hexP), q = p.shiftRight(1);
        return new DHGroup(p, q, TWO);
    }

    private static final String draft_ffdhe2432_p =
          "FFFFFFFFFFFFFFFFADF85458A2BB4A9AAFDC5620273D3CF1"
        + "D8B9C583CE2D3695A9E13641146433FBCC939DCE249B3EF9"
        + "7D2FE363630C75D8F681B202AEC4617AD3DF1ED5D5FD6561"
        + "2433F51F5F066ED0856365553DED1AF3B557135E7F57C935"
        + "984F0C70E0E68B77E2A689DAF3EFE8721DF158A136ADE735"
        + "30ACCA4F483A797ABC0AB182B324FB61D108A94BB2C8E3FB"
        + "B96ADAB760D7F4681D4F42A3DE394DF4AE56EDE76372BB19"
        + "0B07A7C8EE0A6D709E02FCE1CDF7E2ECC03404CD28342F61"
        + "9172FE9CE98583FF8E4F1232EEF28183C3FE3B1B4C6FAD73"
        + "3BB5FCBC2EC22005C58EF1837D1683B2C6F34A26C1B2EFFA"
        + "886B4238611FCFDCDE355B3B6519035BBC34F4DEF99C0238"
        + "61B46FC9D6E6C9077AD91D2691F7F7EE598CB0FAC186D91C"
        + "AEFE13098533C8B3FFFFFFFFFFFFFFFF";
    static final DHGroup draft_ffdhe2432 = fromSafeP(draft_ffdhe2432_p);

    private static final String draft_ffdhe3072_p =
          "FFFFFFFFFFFFFFFFADF85458A2BB4A9AAFDC5620273D3CF1"
        + "D8B9C583CE2D3695A9E13641146433FBCC939DCE249B3EF9"
        + "7D2FE363630C75D8F681B202AEC4617AD3DF1ED5D5FD6561"
        + "2433F51F5F066ED0856365553DED1AF3B557135E7F57C935"
        + "984F0C70E0E68B77E2A689DAF3EFE8721DF158A136ADE735"
        + "30ACCA4F483A797ABC0AB182B324FB61D108A94BB2C8E3FB"
        + "B96ADAB760D7F4681D4F42A3DE394DF4AE56EDE76372BB19"
        + "0B07A7C8EE0A6D709E02FCE1CDF7E2ECC03404CD28342F61"
        + "9172FE9CE98583FF8E4F1232EEF28183C3FE3B1B4C6FAD73"
        + "3BB5FCBC2EC22005C58EF1837D1683B2C6F34A26C1B2EFFA"
        + "886B4238611FCFDCDE355B3B6519035BBC34F4DEF99C0238"
        + "61B46FC9D6E6C9077AD91D2691F7F7EE598CB0FAC186D91C"
        + "AEFE130985139270B4130C93BC437944F4FD4452E2D74DD3"
        + "64F2E21E71F54BFF5CAE82AB9C9DF69EE86D2BC522363A0D"
        + "ABC521979B0DEADA1DBF9A42D5C4484E0ABCD06BFA53DDEF"
        + "3C1B20EE3FD59D7C25E41D2B66C62E37FFFFFFFFFFFFFFFF";
    static final DHGroup draft_ffdhe3072 = fromSafeP(draft_ffdhe3072_p);

    private static final String draft_ffdhe4096_p =
          "FFFFFFFFFFFFFFFFADF85458A2BB4A9AAFDC5620273D3CF1"
        + "D8B9C583CE2D3695A9E13641146433FBCC939DCE249B3EF9"
        + "7D2FE363630C75D8F681B202AEC4617AD3DF1ED5D5FD6561"
        + "2433F51F5F066ED0856365553DED1AF3B557135E7F57C935"
        + "984F0C70E0E68B77E2A689DAF3EFE8721DF158A136ADE735"
        + "30ACCA4F483A797ABC0AB182B324FB61D108A94BB2C8E3FB"
        + "B96ADAB760D7F4681D4F42A3DE394DF4AE56EDE76372BB19"
        + "0B07A7C8EE0A6D709E02FCE1CDF7E2ECC03404CD28342F61"
        + "9172FE9CE98583FF8E4F1232EEF28183C3FE3B1B4C6FAD73"
        + "3BB5FCBC2EC22005C58EF1837D1683B2C6F34A26C1B2EFFA"
        + "886B4238611FCFDCDE355B3B6519035BBC34F4DEF99C0238"
        + "61B46FC9D6E6C9077AD91D2691F7F7EE598CB0FAC186D91C"
        + "AEFE130985139270B4130C93BC437944F4FD4452E2D74DD3"
        + "64F2E21E71F54BFF5CAE82AB9C9DF69EE86D2BC522363A0D"
        + "ABC521979B0DEADA1DBF9A42D5C4484E0ABCD06BFA53DDEF"
        + "3C1B20EE3FD59D7C25E41D2B669E1EF16E6F52C3164DF4FB"
        + "7930E9E4E58857B6AC7D5F42D69F6D187763CF1D55034004"
        + "87F55BA57E31CC7A7135C886EFB4318AED6A1E012D9E6832"
        + "A907600A918130C46DC778F971AD0038092999A333CB8B7A"
        + "1A1DB93D7140003C2A4ECEA9F98D0ACC0A8291CDCEC97DCF"
        + "8EC9B55A7F88A46B4DB5A851F44182E1C68A007E5E655F6A"
        + "FFFFFFFFFFFFFFFF";
    static final DHGroup draft_ffdhe4096 = fromSafeP(draft_ffdhe4096_p);

    private static final String draft_ffdhe6144_p =
          "FFFFFFFFFFFFFFFFADF85458A2BB4A9AAFDC5620273D3CF1"
        + "D8B9C583CE2D3695A9E13641146433FBCC939DCE249B3EF9"
        + "7D2FE363630C75D8F681B202AEC4617AD3DF1ED5D5FD6561"
        + "2433F51F5F066ED0856365553DED1AF3B557135E7F57C935"
        + "984F0C70E0E68B77E2A689DAF3EFE8721DF158A136ADE735"
        + "30ACCA4F483A797ABC0AB182B324FB61D108A94BB2C8E3FB"
        + "B96ADAB760D7F4681D4F42A3DE394DF4AE56EDE76372BB19"
        + "0B07A7C8EE0A6D709E02FCE1CDF7E2ECC03404CD28342F61"
        + "9172FE9CE98583FF8E4F1232EEF28183C3FE3B1B4C6FAD73"
        + "3BB5FCBC2EC22005C58EF1837D1683B2C6F34A26C1B2EFFA"
        + "886B4238611FCFDCDE355B3B6519035BBC34F4DEF99C0238"
        + "61B46FC9D6E6C9077AD91D2691F7F7EE598CB0FAC186D91C"
        + "AEFE130985139270B4130C93BC437944F4FD4452E2D74DD3"
        + "64F2E21E71F54BFF5CAE82AB9C9DF69EE86D2BC522363A0D"
        + "ABC521979B0DEADA1DBF9A42D5C4484E0ABCD06BFA53DDEF"
        + "3C1B20EE3FD59D7C25E41D2B669E1EF16E6F52C3164DF4FB"
        + "7930E9E4E58857B6AC7D5F42D69F6D187763CF1D55034004"
        + "87F55BA57E31CC7A7135C886EFB4318AED6A1E012D9E6832"
        + "A907600A918130C46DC778F971AD0038092999A333CB8B7A"
        + "1A1DB93D7140003C2A4ECEA9F98D0ACC0A8291CDCEC97DCF"
        + "8EC9B55A7F88A46B4DB5A851F44182E1C68A007E5E0DD902"
        + "0BFD64B645036C7A4E677D2C38532A3A23BA4442CAF53EA6"
        + "3BB454329B7624C8917BDD64B1C0FD4CB38E8C334C701C3A"
        + "CDAD0657FCCFEC719B1F5C3E4E46041F388147FB4CFDB477"
        + "A52471F7A9A96910B855322EDB6340D8A00EF092350511E3"
        + "0ABEC1FFF9E3A26E7FB29F8C183023C3587E38DA0077D9B4"
        + "763E4E4B94B2BBC194C6651E77CAF992EEAAC0232A281BF6"
        + "B3A739C1226116820AE8DB5847A67CBEF9C9091B462D538C"
        + "D72B03746AE77F5E62292C311562A846505DC82DB854338A"
        + "E49F5235C95B91178CCF2DD5CACEF403EC9D1810C6272B04"
        + "5B3B71F9DC6B80D63FDD4A8E9ADB1E6962A69526D43161C1"
        + "A41D570D7938DAD4A40E329CD0E40E65FFFFFFFFFFFFFFFF";
    static final DHGroup draft_ffdhe6144 = fromSafeP(draft_ffdhe6144_p);

    private static final String draft_ffdhe8192_p =
          "FFFFFFFFFFFFFFFFADF85458A2BB4A9AAFDC5620273D3CF1"
        + "D8B9C583CE2D3695A9E13641146433FBCC939DCE249B3EF9"
        + "7D2FE363630C75D8F681B202AEC4617AD3DF1ED5D5FD6561"
        + "2433F51F5F066ED0856365553DED1AF3B557135E7F57C935"
        + "984F0C70E0E68B77E2A689DAF3EFE8721DF158A136ADE735"
        + "30ACCA4F483A797ABC0AB182B324FB61D108A94BB2C8E3FB"
        + "B96ADAB760D7F4681D4F42A3DE394DF4AE56EDE76372BB19"
        + "0B07A7C8EE0A6D709E02FCE1CDF7E2ECC03404CD28342F61"
        + "9172FE9CE98583FF8E4F1232EEF28183C3FE3B1B4C6FAD73"
        + "3BB5FCBC2EC22005C58EF1837D1683B2C6F34A26C1B2EFFA"
        + "886B4238611FCFDCDE355B3B6519035BBC34F4DEF99C0238"
        + "61B46FC9D6E6C9077AD91D2691F7F7EE598CB0FAC186D91C"
        + "AEFE130985139270B4130C93BC437944F4FD4452E2D74DD3"
        + "64F2E21E71F54BFF5CAE82AB9C9DF69EE86D2BC522363A0D"
        + "ABC521979B0DEADA1DBF9A42D5C4484E0ABCD06BFA53DDEF"
        + "3C1B20EE3FD59D7C25E41D2B669E1EF16E6F52C3164DF4FB"
        + "7930E9E4E58857B6AC7D5F42D69F6D187763CF1D55034004"
        + "87F55BA57E31CC7A7135C886EFB4318AED6A1E012D9E6832"
        + "A907600A918130C46DC778F971AD0038092999A333CB8B7A"
        + "1A1DB93D7140003C2A4ECEA9F98D0ACC0A8291CDCEC97DCF"
        + "8EC9B55A7F88A46B4DB5A851F44182E1C68A007E5E0DD902"
        + "0BFD64B645036C7A4E677D2C38532A3A23BA4442CAF53EA6"
        + "3BB454329B7624C8917BDD64B1C0FD4CB38E8C334C701C3A"
        + "CDAD0657FCCFEC719B1F5C3E4E46041F388147FB4CFDB477"
        + "A52471F7A9A96910B855322EDB6340D8A00EF092350511E3"
        + "0ABEC1FFF9E3A26E7FB29F8C183023C3587E38DA0077D9B4"
        + "763E4E4B94B2BBC194C6651E77CAF992EEAAC0232A281BF6"
        + "B3A739C1226116820AE8DB5847A67CBEF9C9091B462D538C"
        + "D72B03746AE77F5E62292C311562A846505DC82DB854338A"
        + "E49F5235C95B91178CCF2DD5CACEF403EC9D1810C6272B04"
        + "5B3B71F9DC6B80D63FDD4A8E9ADB1E6962A69526D43161C1"
        + "A41D570D7938DAD4A40E329CCFF46AAA36AD004CF600C838"
        + "1E425A31D951AE64FDB23FCEC9509D43687FEB69EDD1CC5E"
        + "0B8CC3BDF64B10EF86B63142A3AB8829555B2F747C932665"
        + "CB2C0F1CC01BD70229388839D2AF05E454504AC78B758282"
        + "2846C0BA35C35F5C59160CC046FD8251541FC68C9C86B022"
        + "BB7099876A460E7451A8A93109703FEE1C217E6C3826E52C"
        + "51AA691E0E423CFC99E9E31650C1217B624816CDAD9A95F9"
        + "D5B8019488D9C0A0A1FE3075A577E23183F81D4A3F2FA457"
        + "1EFC8CE0BA8A4FE8B6855DFE72B0A66EDED2FBABFBE58A30"
        + "FAFABE1C5D71A87E2F741EF8C1FE86FEA6BBFDE530677F0D"
        + "97D11D49F7A8443D0822E506A9F4614E011E2A94838FF88C"
        + "D68C8BB7C5C6424CFFFFFFFFFFFFFFFF";
    static final DHGroup draft_ffdhe8192 = fromSafeP(draft_ffdhe8192_p);

    
    public static void addNegotiatedDHEGroupsClientExtension(Hashtable extensions, short[] dheGroups)
        throws IOException
    {
        extensions.put(EXT_negotiated_ff_dhe_groups, createNegotiatedDHEGroupsClientExtension(dheGroups));
    }

    public static void addNegotiatedDHEGroupsServerExtension(Hashtable extensions, short dheGroup)
        throws IOException
    {
        extensions.put(EXT_negotiated_ff_dhe_groups, createNegotiatedDHEGroupsServerExtension(dheGroup));
    }

    public static short[] getNegotiatedDHEGroupsClientExtension(Hashtable extensions) throws IOException
    {
        byte[] extensionData = TlsUtils.getExtensionData(extensions, EXT_negotiated_ff_dhe_groups);
        return extensionData == null ? null : readNegotiatedDHEGroupsClientExtension(extensionData);
    }

    public static short getNegotiatedDHEGroupsServerExtension(Hashtable extensions) throws IOException
    {
        byte[] extensionData = TlsUtils.getExtensionData(extensions, EXT_negotiated_ff_dhe_groups);
        return extensionData == null ? -1 : readNegotiatedDHEGroupsServerExtension(extensionData);
    }

    public static byte[] createNegotiatedDHEGroupsClientExtension(short[] dheGroups) throws IOException
    {
        if (dheGroups == null || dheGroups.length < 1 || dheGroups.length > 255)
        {
            throw new TlsFatalAlert(AlertDescription.internal_error);
        }

        return TlsUtils.encodeUint8ArrayWithUint8Length(dheGroups);
    }

    public static byte[] createNegotiatedDHEGroupsServerExtension(short dheGroup) throws IOException
    {
        return TlsUtils.encodeUint8(dheGroup);
    }

    public static short[] readNegotiatedDHEGroupsClientExtension(byte[] extensionData) throws IOException
    {
        short[] dheGroups = TlsUtils.decodeUint8ArrayWithUint8Length(extensionData);
        if (dheGroups.length < 1)
        {
            throw new TlsFatalAlert(AlertDescription.decode_error);
        }
        return dheGroups;
    }

    public static short readNegotiatedDHEGroupsServerExtension(byte[] extensionData) throws IOException
    {
        return TlsUtils.decodeUint8(extensionData);
    }

    public static DHGroup getParametersForDHEGroup(short dheGroup)
    {
        switch (dheGroup)
        {
        case FiniteFieldDHEGroup.ffdhe2432:
            return draft_ffdhe2432;
        case FiniteFieldDHEGroup.ffdhe3072:
            return draft_ffdhe3072;
        case FiniteFieldDHEGroup.ffdhe4096:
            return draft_ffdhe4096;
        case FiniteFieldDHEGroup.ffdhe6144:
            return draft_ffdhe6144;
        case FiniteFieldDHEGroup.ffdhe8192:
            return draft_ffdhe8192;
        default:
            return null;
        }
    }

    public static boolean containsDHCipherSuites(int[] cipherSuites)
    {
        for (int i = 0; i < cipherSuites.length; ++i)
        {
            if (isDHCipherSuite(cipherSuites[i]))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isDHCipherSuite(int cipherSuite)
    {
        switch (TlsUtils.getKeyExchangeAlgorithm(cipherSuite))
        {
        case KeyExchangeAlgorithm.DH_anon:
        case KeyExchangeAlgorithm.DH_anon_EXPORT:
        case KeyExchangeAlgorithm.DH_DSS:
        case KeyExchangeAlgorithm.DH_DSS_EXPORT:
        case KeyExchangeAlgorithm.DH_RSA:
        case KeyExchangeAlgorithm.DH_RSA_EXPORT:
        case KeyExchangeAlgorithm.DHE_DSS:
        case KeyExchangeAlgorithm.DHE_DSS_EXPORT:
        case KeyExchangeAlgorithm.DHE_PSK:
        case KeyExchangeAlgorithm.DHE_RSA:
        case KeyExchangeAlgorithm.DHE_RSA_EXPORT:
            return true;

        default:
            return false;
        }
    }

    public static TlsDHConfig readDHConfig(InputStream input) throws IOException
    {
        BigInteger p = readDHParameter(input);
        BigInteger g = readDHParameter(input);

        TlsDHConfig result = new TlsDHConfig();
        result.setExplicitPG(new BigInteger[]{ p, g });
        return result;
    }

    public static TlsDHConfig receiveDHConfig(TlsDHConfigVerifier dhConfigVerifier, InputStream input) throws IOException
    {
        TlsDHConfig dhConfig = TlsDHUtils.readDHConfig(input);
        if (!dhConfigVerifier.accept(dhConfig))
        {
            throw new TlsFatalAlert(AlertDescription.insufficient_security);
        }
        return dhConfig;
    }

    public static BigInteger readDHParameter(InputStream input) throws IOException
    {
        return new BigInteger(1, TlsUtils.readOpaque16(input));
    }

    public static TlsDHConfig selectDHConfig(DHGroup DHGroup)
    {
        TlsDHConfig result = new TlsDHConfig();
        result.setExplicitPG(new BigInteger[]{ DHGroup.getP(), DHGroup.getG() });
        return result;
    }

    public static void validateDHPublicValues(BigInteger y, BigInteger p) throws IOException
    {
        if (y.compareTo(TWO) < 0 || y.compareTo(p.subtract(TWO)) > 0)
        {
            throw new TlsFatalAlert(AlertDescription.illegal_parameter);
        }

        // TODO See RFC 2631 for more discussion of Diffie-Hellman validation
    }

    public static void writeDHConfig(TlsDHConfig dhConfig, OutputStream output)
        throws IOException
    {
        BigInteger[] pg = dhConfig.getExplicitPG();
        writeDHParameter(pg[0], output);
        writeDHParameter(pg[1], output);
    }

    public static void writeDHParameter(BigInteger x, OutputStream output) throws IOException
    {
        TlsUtils.writeOpaque16(BigIntegers.asUnsignedByteArray(x), output);
    }
}
