package org.bouncycastle.openpgp.operator;

import org.bouncycastle.bcpg.ContainedPacket;
import org.bouncycastle.openpgp.PGPAEADFlavour;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;

/**
 * An encryption method that can be applied to encrypt data in a {@link PGPEncryptedDataGenerator}.
 */
public abstract class PGPKeyEncryptionMethodGenerator
{
    public abstract ContainedPacket generate(int encAlgorithm, byte[] sessionInfo)
        throws PGPException;

    public abstract ContainedPacket generate(PGPAEADFlavour aeadFlavour, int encAlgorithm, int aeadAlgorithm, byte[] sessionInfo)
        throws PGPException;
}
