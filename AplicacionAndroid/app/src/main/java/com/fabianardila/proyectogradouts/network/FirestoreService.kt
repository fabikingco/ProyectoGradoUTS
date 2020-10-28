package com.fabianardila.proyectogradouts.network

import com.fabianardila.proyectogradouts.modelo.*
import com.google.firebase.firestore.FirebaseFirestore

const val LIBROS_COLECTION_NAME = "libros"
const val USER_COLECTION_NAME = "users"
const val CATEGORIA_COLECCION_NAME = "categoriasDeLibros"
const val RESERVAS_COLECCION_NAME = "reservas"

class FirestoreService(val firebaseFirestore: FirebaseFirestore) {

    fun setDocument(data: Any, collectionName: String, id: String, callback: Callback<Void>) {
        firebaseFirestore.collection(collectionName).document(id).set(data)
            .addOnSuccessListener { callback.onSuccess(result = null) }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }

    fun setReserva(data: Reservas, callback: Callback<Void>) {
        firebaseFirestore.collection(RESERVAS_COLECCION_NAME).add(data)
            .addOnSuccessListener { callback.onSuccess(result = null) }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }

    fun deleteReserva(data: Reservas, callback: Callback<Void>) {
        firebaseFirestore.collection(RESERVAS_COLECCION_NAME).document(data.idDocument)
            .delete()
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

    fun getLibrosByFilter(filter: String, dataFilter: String, callback: Callback<List<Libro>>?) {
        firebaseFirestore.collection(LIBROS_COLECTION_NAME)
            .whereEqualTo(filter, dataFilter)
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

    fun listenForUpdatesLibro(libro: Libro, listener: RealtimeDataListener<Libro>) {
        val libroReference = firebaseFirestore.collection(LIBROS_COLECTION_NAME)
        libroReference.document(libro.id!!).addSnapshotListener{snapshot, e ->
            if (e != null) {
                listener.onError(e)
            }
            if (snapshot != null && snapshot.exists()) {
                listener.onDataChange(snapshot.toObject(Libro::class.java)!!)
            }
        }
    }

    fun updateStatusLibro(libro: Libro, listStatus: List<Status>, callback: Callback<Void>) {
        firebaseFirestore.collection(LIBROS_COLECTION_NAME)
            .document(libro.id!!)
            .update("status", listStatus)
            .addOnSuccessListener {
                callback.onSuccess(it)
            }
            .addOnFailureListener { exception -> callback!!.onFailed(exception) }
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

    fun getUserByFilter(filter: String, dataFilter: String, callback: Callback<List<User>>?) {
        firebaseFirestore.collection(USER_COLECTION_NAME)
            .whereEqualTo(filter, dataFilter)
            .get()
            .addOnSuccessListener { documents ->
                val userList = documents.toObjects(User::class.java)
                callback!!.onSuccess(userList)
            }
            .addOnFailureListener { exception ->
                callback!!.onFailed(exception)
            }
    }

    fun updateTipoUser(user: User, callback: Callback<User>?) {
        firebaseFirestore.collection(USER_COLECTION_NAME)
            .document(user.id)
            .update("bibliotecario", user.bibliotecario)
            .addOnSuccessListener {
                callback?.onSuccess(user)
            }
            .addOnFailureListener { exception -> callback!!.onFailed(exception) }
    }

    fun updateUser(user: User, callback: Callback<User>?) {
        firebaseFirestore.collection(USER_COLECTION_NAME)
            .document(user.id)
            .update(mapOf(
                "nombre" to user.nombre,
                "apellido" to user.apellido,
                "programaAcademico" to user.programaAcademico,
                "celular" to user.celular
            ))
            .addOnSuccessListener {
                callback?.onSuccess(user)
            }
            .addOnFailureListener { exception -> callback!!.onFailed(exception) }
    }

    fun getListCategorias(callback: Callback<List<Categoria>>) {
        firebaseFirestore.collection(CATEGORIA_COLECCION_NAME)
            .get()
            .addOnSuccessListener { result ->
                callback.onSuccess(result.toObjects(Categoria::class.java))
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }

    fun getCategorias(callback: Callback<List<Categoria>>) {
        val categoriaList = mutableListOf<Categoria>()
        val categoriaTodos = Categoria("1000", "TODOS")
        categoriaList.add(categoriaTodos)
        firebaseFirestore.collection(CATEGORIA_COLECCION_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (documment in result) {
                    categoriaList.add(documment.toObject(Categoria::class.java))
                }
                callback.onSuccess(categoriaList)
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }

    fun getReservasByUser(mailUser: String, callback: Callback<List<Reservas>>?) {
        firebaseFirestore.collection(RESERVAS_COLECCION_NAME)
            .whereEqualTo("mailUser", mailUser)
            .get()
            .addOnSuccessListener { documents ->
                val reservasList: MutableList<Reservas> = mutableListOf()
                for (document in documents){
                    val reserva = document.toObject(Reservas::class.java)
                    reserva.idDocument = document.id
                    reservasList.add(reserva)
                }
                callback!!.onSuccess(reservasList)
            }
            .addOnFailureListener { exception ->
                callback!!.onFailed(exception)
            }
    }

    fun getReservas(callback: Callback<List<Reservas>>?) {
        firebaseFirestore.collection(RESERVAS_COLECCION_NAME)
            .get()
            .addOnSuccessListener { documents ->
                val reservasList: MutableList<Reservas> = mutableListOf()
                for (document in documents){
                    val reserva = document.toObject(Reservas::class.java)
                    reserva.idDocument = document.id
                    reservasList.add(reserva)
                }
                callback!!.onSuccess(reservasList)
            }
            .addOnFailureListener { exception ->
                callback!!.onFailed(exception)
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