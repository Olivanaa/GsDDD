package br.com.fiap.gs.models;

public class Meta {
	
	private int idMeta;
	private String mensagem;
	private Usuario usuario;
	
	
	public int getIdMeta() {
		return idMeta;
	}
	public void setIdMeta(int idMeta) {
		this.idMeta = idMeta;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
