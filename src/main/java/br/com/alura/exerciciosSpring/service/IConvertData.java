package br.com.alura.exerciciosSpring.service;

public interface IConvertData {
	<T> T getData(String json, Class<T> classe);
}
