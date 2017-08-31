package com.btcwallet.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.btcwallet.enums.Status;
import com.btcwallet.enums.TransactionType;
import com.btcwallet.model.User;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

@Service
public class BitcoinService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;
	
	private NetworkParameters netParams;

	private WalletAppKit kit;

	@Value("${btcwallet.environment}")
	private String environment;

	@PostConstruct
	public void init() {
		if(environment.equals("test")) {
			netParams = TestNet3Params.get();
		}else if(environment.equals("prod")) {
			netParams = MainNetParams.get();
		}
		kit = new WalletAppKit(netParams, new File("/aa"), "aa");

		kit.startAsync();
		kit.awaitRunning();


		kit.wallet().addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
			@Override
			public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
				System.out.println("-----> coins resceived: " + tx.getHashAsString());
				System.out.println("received: " + tx.getValue(wallet));
				
				String bitcoinAddress =  tx.getOutputs().get(0).getAddressFromP2PKHScript(netParams).toString();
				User toUser = userService.findByBitcoinAddress(bitcoinAddress);
				
				com.btcwallet.model.Transaction transaction = new com.btcwallet.model.Transaction();
				transaction.setBitcoinAmount(Double.valueOf("0."+tx.getValue(wallet)).toString());
				transaction.setFromAddress( tx.getInputs().get(0).getFromAddress().toString());
				transaction.setStatus(Status.PENDING);
				transaction.setToUser(toUser);
				transaction.setTransactionDate(new Date());
				transaction.setTransactionType(TransactionType.EXTERNAL);
				
				transactionService.save(transaction);
				
				/*for (TransactionInput txin : tx.getInputs()){
				    System.out.println("from: " + txin.getFromAddress().toString());
				}*/

				Futures.addCallback(tx.getConfidence().getDepthFuture(1), new FutureCallback<TransactionConfidence>() {
					@Override
					public void onSuccess(TransactionConfidence result) {
						//txtLog.append("\nSuccess! Recieved: " + tx.getValue(wallet) + "\n");
						//Find address of sender here
						Double btcRecieved = Double.valueOf("0."+tx.getValue(wallet));
						
						
						
						toUser.setBitcoinAmount(String.valueOf(Double.valueOf(toUser.getBitcoinAmount())+btcRecieved));
						userService.save(toUser);
						
						transaction.setStatus(Status.CONFIRMED);
						transaction.setConfirmationDate(new Date());
						
						transactionService.save(transaction);
					}

					@Override
					public void onFailure(Throwable t) {
						throw new RuntimeException(t);
					}
				});
			}
		});
	}

	public String createAddress(String username) {

		Address address = kit.wallet().freshReceiveAddress();

		kit.wallet().addWatchedAddress(address);

		return  address.toString();
		/*
		if (netParams == RegTestParams.get()) {
		    // Regression test mode is designed for testing and development only, so there's no public network for it.
		    // If you pick this mode, you're expected to be running a local "bitcoind -regtest" instance.
		    kit.connectToLocalHost();
		}

		// Download the block chain and wait until it's done.
		kit.startAsync();


		// Try to read the wallet from storage, create a new one if not possible.

		if(getWallet(username) != null) {
			return;
		}
		Wallet wallet = null;
		final File walletFile = new File(username+".wallet");

		try {
			wallet = new Wallet(netParams);


			if (wallet.getImportedKeys().size() < 1)
				wallet.importKey(new ECKey());

			wallet.saveToFile(walletFile);

		} catch (IOException e) {
			System.out.println("Unable to create wallet file.");
		}

		// fetch the first key in the wallet directly from the keychain ArrayList
		ECKey firstKey = wallet.getImportedKeys().get(0);

		// output key 
		System.out.println("First key in the wallet:\n" + firstKey);

		// and here is the whole wallet
		System.out.println("Complete content of the wallet:\n" + wallet);

		// we can use the hash of the public key
		// to check whether the key pair is in this wallet
		if (wallet.isPubKeyHashMine(firstKey.getPubKeyHash())) {
			System.out.println("Yep, that's my key.");
		} else {
			System.out.println("Nope, that key didn't come from this wallet.");
		}*/
	}

	public Wallet getWallet(String username) {
		/*Wallet wallet = null;
		try {
			wallet = Wallet.loadFromFile(new File(username+".wallet"));
		} catch (UnreadableWalletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(wallet != null) {
			wallet = refreshWallet(username);
		}

		return wallet; */
		//refreshWallet(kit.wallet());
		return kit.wallet();
	}

	public Wallet refreshWallet(Wallet wallet) {
		BlockStore blockStore = new MemoryBlockStore(netParams);
		BlockChain chain = null;
		try {
			chain = new BlockChain(netParams, wallet, blockStore);
		} catch (BlockStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		final PeerGroup peerGroup = new PeerGroup(netParams, chain);
		peerGroup.startAsync();

		// Now download and process the block chain.
		peerGroup.downloadBlockChain();
		peerGroup.stopAsync();
		try {
			wallet.saveToFile(new File(wallet+".wallet"));
		} catch (IOException e) {
			System.out.println("Unable to create wallet file.");
		}

		System.out.println("\nDone!\n");
		System.out.println(wallet.toString());

		return wallet; 
	}

	public void sendCoins(String coin,String toAddress) throws InsufficientMoneyException {

		kit.startAsync();
		kit.awaitRunning();

		System.out.println("Send money to: " + kit.wallet().currentReceiveAddress().toString());


		Coin value = Coin.parseCoin(coin);

		// To which address you want to send the coins?
		// The Address class represents a Bitcoin address.
		Address to = Address.fromBase58(netParams, toAddress);



		Wallet.SendResult result = kit.wallet().sendCoins(kit.peerGroup(), to, value);
		System.out.println("coins sent. transaction hash: " + result.tx.getHashAsString());
		// you can use a block explorer like https://www.biteasy.com/ to inspect the transaction with the printed transaction hash. 


		// shutting down 
		//kit.stopAsync();
		//kit.awaitTerminated();

	}


}
