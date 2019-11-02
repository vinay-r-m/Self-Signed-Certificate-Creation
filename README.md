# Basic HTTPs-Client and HTTPs-Server setup with Self-Signed Certificate.
This repository helps in setting up a basic HTTPs Client and enabling a HTTPs Server and establishing the communication between then using a self-signed certificates.

# Step 1 : Creating a Certificate Authority 
A Certificate Authority is created to authorize/Sign the certificate.
* Create a private key for the Certificate Authority
```
  openssl genrsa -out ca-private.key 4096
```
* Create a public certificate for the Certificate Authority using the generated private key
```
  openssl req -x509 -new -nodes -key ca-private.key -days 1024 -out ca-public.crt
```

# Step 2 : Creating a Certificate for the HTTPs Server 
Create a certificate for the HTTPs server and get it digitally signed with the created CA
* Create a private key for the HTTPs Server
```
  openssl genrsa -out https-server-private.key 2048
```

* Create a certificate signing request 
```
  openssl req -new -key https-server.key -out https-server.csr
```

* Create a conf file to generate the public certificate for the HTTPs Server. 
  * openssl.conf file and its sample contents.
    * Provide all the possible host names of the HTTPs server under the [alt_names] tag 
```
   [req]
   distinguished_name = certificate_info
   req_extensions     = ext
   [certificate_info]
   countryName = IN
   countryName_default = IN
   stateOrProvinceName = KA
   stateOrProvinceName_default = KA
   localityName = BGLR
   localityName_default = BGLR
   organizationalUnitName = SELF
   commonName = https-server
   commonName_max = 64
   [ext ]
   subjectAltName = @alt_names
   [alt_names]
   IP.1 = X.X.X.X
   IP.2 = X.X.X.X
   DNS.1 = server
   DNS.2 = httpsserver
```

* Create a CA signed certificate for the HTTPs Server.
```
  openssl x509 -req -extensions ext -in https-server.csr -CA ca-public.crt -CAkey ca-private.key -CAcreateserial -out https-server-public.crt  -days 500 -extfile openssl.cnf
```

# Step 3 : Enabling HTTPs connector in the Tomcat Server.

* Below are the list of certificate and key files required on the server for enabling https connector.
  *  https-server-public.crt
  *  https-server-private.key
  
* Add the below connector to the $TOMCAT-HOME/conf/server.xml of your tomcat.
> **_NOTE:_**  Having placed the https-server-public.crt and https-server-private.key in the /root/certificate directory the SSLCertificateFile and SSLCertificateKeyFile attributes are configured as shown below.

```
        <Connector protocol="org.apache.coyote.http11.Http11Protocol"
                port="8443" maxThreads="200" scheme="https" secure="true" SSLEnabled="true"
                SSLCertificateFile="/root/certificate/https-server-public.crt"
                SSLCertificateKeyFile="/root/certificate/https-server-private.key"
                SSLVerifyClient="optional" SSLProtocol="TLSv1+TLSv1.1+TLSv1.2" />
```
> **_NOTE:_** * Once the change are done restart the tomcat server
* Once the tomcat server is up, check if the certificate is loaded correctly. 
* The below command should list the DNS names provided during the certificate generation.
```
   openssl s_client -connect 172.16.233.163:8443 |  openssl x509 -noout -text | grep DNS:

```

# Step 4 : HTTPs Client side configuration.
* Below are the list of certificates required on the client for establishing a successful SSL connection.
  *  ca-public.crt 
* Add the CA public certificate to the JVM truststore to authorise the SSL certificate recieved from the HTTPs Server.
```
   keytool -importcert -alias SELF_CERITIFICATE_AUTHORITY -keystore  '$JAVA-HOME\jre\lib\security\cacerts' -storepass changeit -file '/root/certificate/ca-public.crt'
```

# Issue Faced during the setup 
* *java.security.cert.CertificateException: No subject alternative names matching IP address x.x.x.x found.*
* *java.security.NoSuchAlgorithmException*
* *SignatureException: Signature length not correct*

## Solutions 
### How to resolve java.security.cert.CertificateException: No subject alternative names matching IP address x.x.x.x found.* in the right way ?


### How to resolve java.security.NoSuchAlgorithmException ?

### How to resolve RSA SignatureException: Signature length not correct?

