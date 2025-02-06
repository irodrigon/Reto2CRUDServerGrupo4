/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.tartanga.grupo4.creditcards.CreditCard;
import com.tartanga.grupo4.customers.Admin;
import com.tartanga.grupo4.exceptions.CreateException;
import com.tartanga.grupo4.exceptions.ReadException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.UrlBase64;
import security.Hash;

/**
 *
 * @author Iñi
 */
@Stateless
@Path("com.tartanga.grupo4.customers.admin")
public class AdminFacadeREST extends AbstractFacade<Admin> {

    static {
        //Poner BouncyCastle como provider
        Security.addProvider(new BouncyCastleProvider());
    }

    private Hash security = new Hash();
    private static final Logger logger = Logger.getLogger(AdminFacadeREST.class.getName());

    @PersistenceContext(unitName = "Reto2CRUDServerGrupo4PU")
    private EntityManager em;

    public AdminFacadeREST() {
        super(Admin.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Admin entity) {
        try {
            String password = desencriptar(entity.getPassword());
            String hash = security.passwordToHash(password);
            entity.setPassword(hash);
        } catch (NoSuchAlgorithmException error) {
            logger.log(Level.SEVERE, "RovoBankSignUpController: Exception while creating Hash, {0}", error.getMessage());
            throw new InternalServerErrorException();
        }
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Admin entity) {
        try {
            String password = desencriptar( entity.getPassword());
            String hash = security.passwordToHash(password);
            entity.setPassword(hash);
            super.edit(entity);
        } catch (Exception error) {
            logger.log(Level.SEVERE, "RovoBankSignUpController: Exception while updating the user, {0}", error.getMessage());
            throw new InternalServerErrorException();
        }

    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Admin find(@PathParam("id") String id) {
        Admin admin = new Admin();
        try {
            admin = super.find(id);
        } catch (Exception error) {
            logger.log(Level.SEVERE, "AdminFacadeREST: Exception getting admin by logIn: {0}: ", error.getMessage());
            throw new NotAuthorizedException(error.getMessage());
        }

        return admin;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Admin> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Admin> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("credentials/{logIn}/{password}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Admin findAdminByCredentials(@PathParam("logIn") String logIn, @PathParam("password") String password) throws ReadException {

        Admin admin = new Admin();
        try {
            password = desencriptar(password);
            //Hashear el password 
            String hash = security.passwordToHash(password);

            password = hash;
            logger.log(Level.INFO, "AdminFacadeREST: Searching data by logIn and password.");
            admin = (Admin) em.createNamedQuery("findAdminByCredentials").setParameter("logIn", logIn).setParameter("password", password).getSingleResult();

        } catch (NoSuchAlgorithmException error) {
            logger.log(Level.SEVERE, "RovoBankSignUpController: Exception while creating Hash, {0}", error.getMessage());
            throw new InternalServerErrorException();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "AdminFacadeREST: Exception reading data by logIn and password {0}: ", e.getMessage());
            throw new NotAuthorizedException(e.getMessage());
        }

        return admin;
    }

    @GET
    @Path("credentials/{logIn}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Admin countAdminByLogin(@PathParam("logIn") String logIn) throws ReadException {

        Admin admin = new Admin();

        try {
            logger.log(Level.INFO, "AdminFacadeREST: Searching data by logIn and password.");
            admin = (Admin) em.createNamedQuery("countAdminByLogin").setParameter("logIn", logIn).getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "AdminFacadeREST: Exception reading data by logIn and password: ", e.getMessage());
            throw new ReadException(e.getMessage());
        }

        return admin;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private String desencriptar(String password) {
        try {

            InputStream input = AdminFacadeREST.class.getClassLoader().getResourceAsStream("security/Private.key");
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(data)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            input.close();

            byte[] privateKeyBytes = buffer.toByteArray();

            //Recontruir la llave privada
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
            PrivateKey privateKey = keyFactory.generatePrivate(spec);

            //Deconvertirlo de BASE64
            byte[] encryptedPass = UrlBase64.decode(password);

            //Desencriptar la password
            Cipher cipher = Cipher.getInstance("ECIES", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decriptedPass = cipher.doFinal(encryptedPass);
            password = new String(decriptedPass);
        } catch (Exception error) {
            //error.printStackTrace();
            logger.log(Level.SEVERE, "AdminFacadeREST: Exception while decripting the password: ", error.getMessage());
            throw new InternalServerErrorException();
        }
        return password;

    }

}
