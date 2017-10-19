/**
 * 
 */
package soen6441team15.soen6441;


import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.RWMapFileController;
import controller.ReadController;
import controller.WriteController;
import model.DataReader;
import model.DataWriter;
import model.LoadMap;
import model.MapDataBase;
import model.Territory;
import model.contract.ITerritory;

/**
 * @author RTCC
 *
 */
public class MapIOTest {

	static WriteController writeController;
	static ReadController readController;
	
	@Before
	public void setUpBeforeClass()
	{
		 RWMapFileController rw = new RWMapFileController();
	     rw.loadMap(new File("C:\\Users\\m_guntur\\Downloads\\Annys World\\Annys World.map"));
	     DataReader dataReader = new DataReader();
	     readController = new ReadController(dataReader);
	     DataWriter dataWriter = new DataWriter();
	     writeController = new WriteController(dataWriter); 
	}
	
	
	
	@Test
	public void readerTest(){
	     assertFalse(readController.getContinentValue("annys world") == 4);
	     assertTrue(readController.dataReader.hasContinent("afrika"));
	}
	
	@Test
	public void addToMapTest(){
		writeController.addData("[America,Newzland]", "Kontinent", "Kontry", "4", false, false);
		String[] arr = {"America","Newzland"} ;
	    System.out.println(readController.getAdjacentTerritories("Kontinent", "Kontry"));
		assertTrue(readController.getContinentValue("Kontinent") == 4);
	    assertTrue(readController.getAdjacentTerritories("Kontinent", "Kontry").get(0).equals("America"));
	}
	
	@Test
	public void deleteOnMapTest(){
		writeController.addData("[America,Newzland]", "Kontinent", "Kontry", "4", false, false);
		writeController.addData("America,Newzland", "Kontinent", "Kontry", "4", true, false);
		assertFalse(readController.dataReader.hasContinent("Kontinent"));
	}
	
	@Test
	public void adjacencyTest(){
		Territory t= MapDataBase.continents.get("atlantis").get("was");
		ArrayList<ITerritory> tmp= t.getAdjacentTerritoryObjects();
		System.out.println(tmp.size());
		for(ITerritory t2:tmp){
			System.out.println(t2.getName());
		}
	}
	
	@Test
	@Ignore
	public void test() {
		LoadMap loadMap = new LoadMap(new File("E:\\Compressed\\_61_ CASTLE MOONBAT\\_61_ CASTLE MOONBAT.map"));
        loadMap.load();
		HashMap<String, Territory> terrotories= MapDataBase.continents.get("Tower Left Top");
		for(Territory t: terrotories.values()){
			System.out.println("\n Name "+t.getTerritoryName());
			ArrayList<String> adjacents= t.getAdjacentTerritories();
			for(String s:adjacents){
				System.out.print(s);
			}
		}
	}

}
