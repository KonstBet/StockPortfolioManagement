# ΠΧ2. Διαχείριση Υπολοίπου Χρήστη

**Πρωτεύων Actor**: Χρήστης  
**Ενδιαφερόμενοι**  
**Χρήστης**: Θέλει να κάνει κατάθεση ή ανάληψη χρημάτων από το ψηφιακό του πορτοφόλι.    
**Προϋποθέσεις**: Ο Χρήστης έχει εκτελέσει με επιτυχία τις περιπτώσεις χρήσης Δημιουργία προφίλ και Αυθεντικοποίηση Χρήστη.

## Βασική Ροή

### Α) Κατάθεση Χρηματικού Ποσού

1. Ο Χρήστης πηγαίνει στο ψηφιακό του πορτοφόλι και επιλέγει το ποσό που θέλει να καταθέσει.
2. Το σύστημα ελέγχει αν στο μέσω από το οποίο ο Χρήστης καταθέτει υπάρχει διαθέσιμο το ποσό που επέλεξε ο Χρήστης.
3. Το σύστημα καταγράφει την συναλλαγή και το υπόλοιπο του χρήστη ενημερώνεται.

**Εναλλακτικές Ροές**

*2α. Το απαιτούμενο χρηματικό ποσό δεν είναι διαθέσιμο*
1. Το σύστημα ενημερώνει τον χρήστη ότι το ποσό που θέλει να καταθέσει δεν ειναι διαθέσιμο.
2. Η περίπτωση χρήσης επιστρέφει στο βήμα 3.

### Β) Ανάληψη Χρηματικού Ποσού

1. Ο Χρήστης πηγαίνει στο ψηφιακό του πορτοφόλι και επιλέγει το ποσό που θέλει να κάνει ανάληψη.
2. Το σύστημα ελέγχει αν το ζητούμενο ποσό είναι διαθέσιμο στο ψηφιακό πορτοφόλι του Χρήστη.
3. Το σύστημα καταγράφει την συναλλαγή και το υπόλοιπο του χρήστη ενημερώνεται.

**Εναλλακτικές Ροές**

*2α. Το απαιτούμενο χρηματικό ποσό δεν είναι διαθέσιμο*
1. Το σύστημα ενημερώνει τον χρήστη ότι το ψηφιακό του πορτοφόλι δεν διαθέτει το απαιτούμενο ποσό.
2. Η περίπτωση χρήσης επιστρέφει στο βήμα 3.

### Γ) Ενημέρωση Υπολοίπου

1. Ο Χρήστης πάει στο ψηφιακό του πορτοφόλι και ζητάει να ενημερωθεί για το υπόλοιπο του.
2. Το σύστημα υπολογίζει το υπόλοιπο του Χρήστη.
3. Το σύστημα εκτυπώνει το υπόλοιπο του Χρήστη.

