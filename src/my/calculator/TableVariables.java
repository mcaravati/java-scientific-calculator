/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.calculator;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author owl
 */
class VariablesTable implements my.calculator.expr.Environnement {

    Map<String, Double> variables = new HashMap<>();

    public void apply(String name, double value) {
        variables.put(name, value);
    }

    public double value(String name) {
        return variables.get(name);
    }

    public boolean contains(String name) {
        return variables.containsKey(name);
    }
}
