/******************************************************************************
 *
 *  Copyright 2014 Paphus Solutions Inc.
 *
 *  Licensed under the Eclipse Public License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/
package org.botlibre.test;

import java.util.List;
import java.util.logging.Level;

import org.botlibre.Bot;
import org.botlibre.sense.text.TextEntry;
import org.botlibre.thought.language.Language;
import org.botlibre.thought.language.Language.LearningMode;
import org.botlibre.util.Utils;
import org.junit.BeforeClass;

/**
 * Test importing data from WikiData.
 */

public class TestWikidataFR extends TextTest {
	
	public static int SLEEP = 5000;

	@BeforeClass
	public static void setup() {
		bootstrap("fr");
	}

	@org.junit.Test
	public void testImport() throws Exception {
		//Utils.sleep(SLEEP);
		Bot bot = Bot.createInstance();
		//Sense sense = bot.awareness().getSense(Http.class.getName());
		//sense.input(new URL("http://www.freebase.com/physics/particle"));
		//Utils.sleep(5000);
		
		bot.shutdown();
	}

	@org.junit.Test
	public void testWhatIs() {
		Utils.sleep(SLEEP);
		Bot bot = Bot.createInstance();
		//bot.setDebugLevel(Level.FINER);
		Language language = bot.mind().getThought(Language.class);
		language.setLearningMode(LearningMode.Disabled);
		language.setLanguage("fr");
		TextEntry text = bot.awareness().getSense(TextEntry.class);
		List<String> output = registerForOutput(text);
		
		text.input("Qu'est-ce que l'Uranium?");
		String response = waitForOutput(output);
		if (!response.equals("??l??ment chimique ayant le num??ro atomique 92")) {
			fail("Incorrect: " + response);
		}
		
		Utils.sleep(SLEEP);
		text.input("Qu'est-ce que Oxyg??ne");
		response = waitForOutput(output);
		if (!response.equals("??lement chimique ayant le num??ro atomique 8")) {
			fail("Incorrect: " + response);
		}

		Utils.sleep(SLEEP);
		text.input("D??finir eau");
		response = waitForOutput(output);
		if (!response.equals("Liquide transparent, incolore, inodore et insipide ?? l?????tat pur, qui est le principal constituant des lacs, rivi??res, mers et oc??ans.")) {
			fail("Incorrect: " + response);
		}

		Utils.sleep(SLEEP);
		text.input("Qui est Barack Obama?");
		response = waitForOutput(output);
		if (response.indexOf("pr??sident des ??tats-Unis de 2009 ?? 2017") == -1) {
			fail("Incorrect: " + response);
		}

		Utils.sleep(SLEEP);
		text.input("Qui est son p??re?");
		response = waitForOutput(output);
		if (response.indexOf("Severin Obama") == -1 && response.indexOf("Barack Obama") == -1) {
			fail("Incorrect: " + response);
		}

		Utils.sleep(SLEEP);
		text.input("Qui est George Bush?");
		response = waitForOutput(output);
		if (response.indexOf("homme d'??tat am??ricain, pr??sident des ??tats-Unis de 1989 ?? 1993") == -1) {
			fail("Incorrect: " + response);
		}

		Utils.sleep(SLEEP);
		text.input("Qui est Barack Obama?");
		response = waitForOutput(output);
		if (response.indexOf("pr??sident des ??tats-Unis de 2009 ?? 2017") == -1) {
			fail("Incorrect: " + response);
		}

		Utils.sleep(SLEEP);
		text.input("Qui est sa m??re?");
		response = waitForOutput(output);
		if (response.indexOf("Ann Dunham") == -1) {
			fail("Incorrect: " + response);
		}

		bot.shutdown();
	}
	
	@org.junit.Test
	public void testWhereIs() {
		Utils.sleep(SLEEP);
		Bot bot = Bot.createInstance();
		//bot.setDebugLevel(Level.FINER);
		Language language = bot.mind().getThought(Language.class);
		language.setLearningMode(LearningMode.Disabled);
		language.setLanguage("fr");
		TextEntry text = bot.awareness().getSense(TextEntry.class);
		List<String> output = registerForOutput(text);

		Utils.sleep(SLEEP);
		text.input("O?? est Montr??al?");
		String response = waitForOutput(output);
		checkResponse(response, "Montr??al est au Canada.");

		Utils.sleep(SLEEP);
		text.input("O?? est Edmonton?");
		response = waitForOutput(output);
		checkResponse(response, "Edmonton est au Canada.");
		
		// Test twice for when already known.
		Utils.sleep(SLEEP);
		text.input("O?? est Edmonton?");
		response = waitForOutput(output);
		checkResponse(response, "Edmonton est au Canada.");
		
		text.input("x");
		response = waitForOutput(output);
		
		// Test lower case.
		Utils.sleep(SLEEP);
		text.input("O?? est edmonton?");
		response = waitForOutput(output);
		checkResponse(response, "Edmonton est au Canada.");

		Utils.sleep(SLEEP);
		text.input("Qu'est-ce que Brockville?");
		response = waitForOutput(output);
		checkResponse(response, "ville de l'Ontario (Canada)");

		// Test twice for when already known.
		Utils.sleep(SLEEP);
		text.input("Qu'est-ce que Brockville?");
		response = waitForOutput(output);
		checkResponse(response, "ville de l'Ontario (Canada)");

		text.input("x");
		response = waitForOutput(output);
		
		// Test lower case.
		Utils.sleep(SLEEP);
		text.input("Qu'est-ce que brockville?");
		response = waitForOutput(output);
		checkResponse(response, "ville de l'Ontario (Canada)");

		Utils.sleep(SLEEP);
		text.input("O?? est-ce?");
		response = waitForOutput(output);
		checkResponse(response, "C'est au Canada.");

		bot.shutdown();
	}
}

