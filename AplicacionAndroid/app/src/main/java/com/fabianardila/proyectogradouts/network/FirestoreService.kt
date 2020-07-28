package com.fabianardila.proyectogradouts.network

import com.fabianardila.proyectogradouts.modelo.Libro
import com.fabianardila.proyectogradouts.modelo.User
import com.google.firebase.firestore.FirebaseFirestore

const val LIBROS_COLECTION_NAME = "libros"
const val USER_COLECTION_NAME = "users"

class FirestoreService(val firebaseFirestore: FirebaseFirestore) {

    fun setDocument(data: Any, collectionName: String, id: String, callback: Callback<Void>) {
        firebaseFirestore.collection(collectionName).document(id).set(data)
            .addOnSuccessListener { callback.onSuccess(result = null) }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }

    fun getLibros(callback: Callback<List<Libro>>?) {
        firebaseFirestore.collection(LIBROS_COLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (documment in result) {
                    val libroList = result.toObjects(Libro::class.java)
                    callback!!.onSuccess(libroList)
                    break
                }
            }
            .addOnFailureListener { exception ->
                callback!!.onFailed(exception)
            }
    }

    fun getLibrosByCategoria(categoria: String, callback: Callback<List<Libro>>?) {
        firebaseFirestore.collection(LIBROS_COLECTION_NAME)
            .whereEqualTo("categoria", categoria)
            .get()
            .addOnSuccessListener { documents ->
                val libroList = documents.toObjects(Libro::class.java)
                callback!!.onSuccess(libroList)
            }
            .addOnFailureListener { exception ->
                callback!!.onFailed(exception)
            }
    }

    fun listenForUpdates(libros: List<Libro>, listener: RealtimeDataListener<Libro>) {
        val libroReference = firebaseFirestore.collection(LIBROS_COLECTION_NAME)
        for (libro in libros) {
            libroReference.document(libro.id!!).addSnapshotListener{snapshot, e ->
                if (e != null) {
                    listener.onError(e)
                }
                if (snapshot != null && snapshot.exists()) {
                    listener.onDataChange(snapshot.toObject(Libro::class.java)!!)
                }
            }
        }
    }

    fun getUsuarios(callback: Callback<List<User>>?) {
        firebaseFirestore.collection(USER_COLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (documment in result) {
                    val userList = result.toObjects(User::class.java)
                    callback!!.onSuccess(userList)
                    break
                }
            }
            .addOnFailureListener { exception ->
                callback!!.onFailed(exception)
            }
    }

    fun findUserByEmail(email: String, callback: Callback<User>) {
        firebaseFirestore.collection(USER_COLECTION_NAME)
            .whereEqualTo("correo", email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    callback.onSuccess(document.toObject(User::class.java))
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }

    /*fun updateUser(user: User, callback: Callback<User>?) {
        firebaseFirestore.collection(USER_COLECTION_NAME)
            .document(user.username)
            .update("cryptoList", user.cryptoList)
            .addOnSuccessListener {
                callback?.onSuccess(user)
            }
            .addOnFailureListener { exception -> callback!!.onFailed(exception) }
    }

    fun updateCrypto(crypto: Crypto) {
        firebaseFirestore.collection(CRYPTO_COLECTION_NAME).document(crypto.getDocumentId())
            .update("available", crypto.available)
    }

    fun getCryptos(callback: Callback<List<Crypto>>?) {
        firebaseFirestore.collection(CRYPTO_COLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (documment in result) {
                    val cryptoList = result.toObjects(Crypto::class.java)
                    callback!!.onSuccess(cryptoList)
                    break
                }
            }
            .addOnFailureListener { exception ->
                callback!!.onFailed(exception)
            }
    }

    fun findUserById(id: String, callback: Callback<User>) {
        firebaseFirestore.collection(USER_COLECTION_NAME)
            .document(id)
            .get()
            .addOnSuccessListener { result ->
                if (result.data != null) {
                    callback.onSuccess(result.toObject(User::class.java))
                } else {
                    callback.onSuccess(null)
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }

    fun listenForUpdates(cryptos: List<Crypto>, listener: RealtimeDataListener<Crypto>) {
        val cryptoReference = firebaseFirestore.collection(CRYPTO_COLECTION_NAME)
        for (crypto in cryptos) {
            cryptoReference.document(crypto.getDocumentId()).addSnapshotListener{snapshot, e ->
                if (e != null) {
                    listener.onError(e)
                }
                if (snapshot != null && snapshot.exists()) {
                    listener.onDataChange(snapshot.toObject(Crypto::class.java)!!)
                }
            }
        }
    }

    fun listenForUpdates(user: User, listener: RealtimeDataListener<User>) {
        val userReference = firebaseFirestore.collection(USER_COLECTION_NAME)

        userReference.document(user.username).addSnapshotListener{snapshot, e ->
            if (e != null) {
                listener.onError(e)
            }
            if (snapshot != null && snapshot.exists()) {
                listener.onDataChange(snapshot.toObject(User::class.java)!!)
            }
        }
    }*/

}