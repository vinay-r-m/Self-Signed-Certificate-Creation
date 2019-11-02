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
  openssl req -new -key ca-private.key -out https-server.csr
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


# Step 3 : Enabling HTTPs connectors in the Tomcat.




# Issue Faced during the setup 
* *java.security.cert.CertificateException: No subject alternative names matching IP address x.x.x.x found.*
* *java.security.NoSuchAlgorithmException*
## Solutions 
### How to resolve java.security.cert.CertificateException in the right way ?


### How to resolve java.security.NoSuchAlgorithmException ?

