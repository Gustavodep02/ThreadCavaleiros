package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadCavaleiro extends Thread {
	Random random = new Random();
	private int permissoes;
	private int tocha;
	private int pedra;
	private int id;
	private int [] vetPorta;
	Semaphore semaforo = new Semaphore(permissoes);

	public ThreadCavaleiro(int tocha, int pedra, int id,int[] vetPorta, int permissoes, Semaphore semaforo) {
		this.semaforo = semaforo;
		this.permissoes = permissoes;
		this.tocha = tocha;
		this.pedra = pedra;
		this.id = id;
		this.vetPorta = vetPorta;
	}

	@Override
	public void run() {
		int velocidade = (int) ((Math.random() * 3) + 2);
		int distancia = 2000;
		caminhar(velocidade, distancia);
	}

	public int bonusTocha(int velocidade) {
		System.out.println("Cavaleiro "+id+" pegou a tocha!");
		return velocidade+2;
		
	}
	public int bonusPedra(int velocidade) {
		System.out.println("Cavaleiro "+id+" pegou a pedra!");
		return velocidade+2;
	}

	public void caminhar(int velocidade, int distanciaTotal) {
		int distanciaAtual = 0;
		while (distanciaAtual < 2000) {
			try {
				Thread.sleep(50);
				System.out.println("Cavaleiro "+id+" já andou "+distanciaAtual+" metros");
				distanciaAtual += velocidade;
				
				if (distanciaAtual>=500 && tocha==0) {
					tocha++;
					velocidade = bonusTocha(velocidade);
				}else if(distanciaAtual>=1500 && pedra ==0){
					pedra++;
					velocidade = bonusPedra(velocidade);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Cavaleiro "+id+" terminou o caminho");
		try {
			semaforo.acquire();
			porta();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			semaforo.release();
		}
		

	}
	public void porta() {
		int num = (int) ((Math.random() * 4));
		while(vetPorta[num]!=0) {
			num = (int) ((Math.random() * 4));
		}
		vetPorta[num]++;
		if(num == 3) {
			System.out.println("Cavaleiro "+id+" achou a saida na porta "+(num+1));
		}else {
			System.out.println("Cavaleiro "+id+" foi devorado por um monstro na porta "+(num+1));
		}
	}
}
