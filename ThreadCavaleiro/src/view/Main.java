package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCavaleiro;

public class Main {

	public static void main(String[] args) {
		int permissoes = 1;
		Semaphore semaforo = new Semaphore(permissoes);
		int cavaleiros = 4;
		int vetPorta[] = {0,0,0,0};
		for(int i = 0; i<cavaleiros;i++) {
		ThreadCavaleiro t = new ThreadCavaleiro(i+1,vetPorta, permissoes, semaforo );
		t.start();
		}

	}

}