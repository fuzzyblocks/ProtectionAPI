/*
 * Copyright (c) 2013, LankyLord
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.fuzzyblocks.protectionapi;

import org.bukkit.ChatColor;

import java.util.Map;

public class ConfigurationManager {

    private static Map<String, String> strings;

    public static void setString(String stringName, String stringValue) {
        if (strings.containsKey(stringName)) {
            strings.remove(stringName);
            strings.put(stringName, stringValue);
        }
    }

    public static String getString(String stringName) {
        return strings.get(stringName);
    }

    private void loadStrings() {
        strings.put("NOPERM_BLOCK_BREAK", ChatColor.RED + "You are not allowed to break blocks here.");
        strings.put("NOPERM_BLOCK_DAMAGE", ChatColor.RED + "You are not allowed to eat that cake.");
        strings.put("NOPERM_BLOCK_IGNITE", ChatColor.RED + "You are not allowed to ignite that block.");
    }
//TODO: Add string loader, Strings should be provided by the front end
}
