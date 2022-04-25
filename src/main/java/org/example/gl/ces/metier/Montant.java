package org.example.gl.ces.metier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

/**
 * Value-Object représentant un montant.
 *
 * Cette classe gère le fait qu'un montant en Nouvelle-Calédonie est globalement sans centimes;
 * sauf pour les certains calculs.
 * 
 * Pour créer une instance, il faut utiliser {@link #montantFixe(long)} ou {@link #montantDecimal(double)}
 */
public class Montant {

    /** Nombre de décimales pour un montant */
    private static final int NB_DECIMALES = 2;

    /** Mode d'arrondi utilisé lorsque c'est nécessaire. */
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    /**
     * Créer un montant sans décimale.
     * @param valeur La valeur du montant
     * @return Le montant créé.
     */
    public static Montant montantFixe(long valeur) {
        return new Montant(BigDecimal.valueOf(valeur), false);
    }

    /**
     * Créer un montant avec 2 décimales.
     *
     * La valeur est arrondie en mode {@link RoundingMode#FLOOR}.
     *
     * @param valeur La valeur du montant
     * @return Le montant créé.
     */
    public static Montant montantDecimal(double valeur) {
        return new Montant(BigDecimal.valueOf(valeur), true);
    }

    private final BigDecimal valeur;
    private final boolean decimal;

    private Montant(BigDecimal valeur, boolean decimal) {
        if (decimal) {
            this.valeur = valeur.setScale(NB_DECIMALES, ROUNDING_MODE);
        } else {
            this.valeur = valeur.setScale(0, RoundingMode.UNNECESSARY);
        }
        this.decimal = decimal;
    }

    /**
     * @return <code>true</code> si le montant est positif (0 n'est pas considéré positif)
     */
    public boolean isPositif() {
        return this.valeur.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * @return <code>true</code> si le montant est négatif (0 n'est pas considéré négatif)
     */
    public boolean isNegatif() {
        return this.valeur.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Multiplie le montant par un entier
     * @param facteur l'entier avec lequel on veut multiplier le montant
     * @return Le nouveau montant. Si le montant était décimal, il le reste.
     */
    public Montant multiplierPar(int facteur) {
        var newValeur = this.valeur.multiply(new BigDecimal(facteur));
        return new Montant(newValeur, this.decimal);
    }

    /**
     * Divise le montant par un entier.
     *
     * @param facteur l'entier avec lequel on veut diviser le montant
     * @return Le nouveau montant. Si le montant resultant est décimal. Le montant est arrondi avec FLOOR
     */
    public Montant diviserPar(int facteur) {
        var newValeur = this.valeur;
        if (!this.decimal) {
            // Force this.valeur a être décimale, sinon on a une division entière et on perd la partie décimale
            newValeur = newValeur.setScale(NB_DECIMALES, RoundingMode.UNNECESSARY);
        }
        newValeur = newValeur.divide(new BigDecimal(facteur), ROUNDING_MODE);
        return new Montant(newValeur, true);
    }

    /**
     * Multiplie le montant par un taux.
     * @param taux le taux avec lequel on veut multiplier le montant
     * @return Le nouveau montant. Il est decimal.
     */
    public Montant appliquerTaux(double taux) {
        var newValeur = this.valeur.multiply(new BigDecimal(taux));
        return new Montant(newValeur, true);
    }

    /**
     * Additionne <code>autreMontant</code> à ce montant.
     *
     * @param autreMontant Le montant autre à additionner à <code>this</code>
     * @return Le nouveau montant. Il est décimal si <code>this</code> ou <code>autreMontant</code> est décimal.
     */
    public Montant additionner(Montant autreMontant) {
        var newDecimal = this.decimal || autreMontant.decimal;
        return new Montant(this.valeur.add(autreMontant.valeur), newDecimal);
    }

    /**
     * Soustrait <code>autreMontant</code> à ce montant.
     *
     * @param autreMontant Le montant autre à soustraire à <code>this</code>
     * @return Le nouveau montant. Il est décimal si <code>this</code> ou <code>autreMontant</code> est décimal.
     */
    public Montant soustraire(Montant autreMontant) {
        return this.additionner(autreMontant.multiplierPar(-1));
    }


    /**
     * @return Le montant arrondi sans centimes.
     */
    public Montant arrondir() {
        return arrondir(0);
    }

    /**
     * @return Le montant arrondi aux nombres de décimales spécifié.
     */
    public Montant arrondir(int nbDecimales) {
        return new Montant(this.valeur.setScale(nbDecimales, ROUNDING_MODE), nbDecimales != 0);
    }

    /**
     * @return Le montant sous la forme d'une chaîne pour les humains. Le symbole monétaire utilisé est 'F'.
     */
    public String format() {
        // Par défaut en français le séparateur de milliers est un espace insécable.
        // On utilise donc nos propres symboles pour utiliser l'espace normal.
        var symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        symbols.setCurrencySymbol("F");

        var formatter = new DecimalFormat("###,###,###,###.00 F", symbols);
        if (this.decimal) {
            formatter.setMaximumFractionDigits(NB_DECIMALES);
        } else {
            formatter.setMinimumFractionDigits(0);
        }
        return formatter.format(this.valeur);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Montant montant = (Montant) o;
        return Objects.equals(valeur, montant.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }

    @Override
    public String toString() {
        return "Montant{" +
            "valeur=" + valeur +
            ", decimal=" + decimal +
            '}';
    }

}
