# ΠΧ4. Παραγωγή Αναφοράς Κατάστασης Χαρτοφυλακίου

**Πρωτεύων Actor**: Χρήστης
**Ενδιαφερόμενοι**
**Χρήστης**: Θέλει να ενημερωθεί για την κατάσταση του χαρτοφυλακίου του
**Προϋποθέσεις**: Ο Χρήστης έχει εκτελέσει με επιτυχία την περίπτωση χρήσης Δημιουργία Προφίλ

## Βασική Ροή
1. Ο Χρήστης εισάγει τα στοιχεία του προσωπικού λογαριασμού του (Όνομα Χρήστη και Συνθηματικό).
2. Το σύστημα επιβεβαιώνει την ορθότητα των στοιχείων.
3. Ο Χρήστης πηγαίνει στο χαρτοφυλάκιο του και επιλέγει εκτύπωση κατάστασης χαρτοφυλακίου.
4. Το σύστημα παράγει μια αναφόρα με την κατάσταση του χαρτοφυλακίου του Χρήστη.
5. Το σύστημα εκτυπώνει την αναφορα του χαρτοφυλακίου του Χρήστη.

**Εναλλακτικές Ροές**
*1α. Ο χρήστης είναι ήδη συνδεδεμένος*
1. Η περίπτωση χρήσης πάει στο βήμα 3

*2α. Τα στοιχεία που εισήγαγε ο χρήστης δεν αντιστοιχούν σε κάποιον λογαριασμό*
1. Το σύστημα ενημερώνει τον χρήστη ότι τα στοιχεία που εισήγαγε δεν αντιστοιχούν σε κάποιον λογαριασμό.
2. Η περίπτωση χρήσης επιστρέφει στο βήμα 1.