# ΠΧ1. Διαχείριση Προφίλ Επενδυτή

**Πρωτεύων Actor**: Χρήστης  
**Ενδιαφερόμενοι**
**Χρήστης**: Θέλει να εισέλθει στο χρηματηστήριο και να επενδύσει σε μετοχές  
**Προϋποθέσεις**: 

## Βασική Ροή

### Α) Δημιουργία Προφίλ

1. Ο χρήστης επιλέγει Δημιουργία Λογαριασμού.
2. Το σύστημα εμφανίζει τη φόρμα εγγραφής/συμπλήρωσης στοιχείων του χρήστη (ονοματεπώνυμο, email, κλπ).
3. Ο χρήστης συμπληρώνει τα στοιχεία του.
4. Ο χρήστης Αποδέχεται τους όρους εγγραφής και επιλέγει δημιουργία.
5. Το σύστημα δημιουργεί το χρήστη.
6. Το σύστημα εμφανίζει κατάλληλο μήνυμα ολοκλήρωσης εγγραφής

**Εναλλακτικές Ροές**

*3α. Έγινε εισαγωγή λανθασμένων στοιχείων.*  
1. Το σύστημα εμφανίζει κατάλληλο μήνυμα σφάλματος
2. Η ΠΧ επιστρέφει στο βήμα 2

*3β. Δεν έγινε εισαγωγή υποχρεωτικών στοιχείων.*  
1. Το σύστημα εμφανίζει κατάλληλο μήνυμα
2. Η ΠΧ επιστρέφει στο βήμα 2

*4α. Ο χρήστης δεν αποδέχεται τους όρους χρήσης.*  
1. Το σύστημα εμφανίζει κατάλληλο μήνυμα
2. Η ΠΧ επιστρέφει στο βήμα 4

*4β. Βρέθηκε ήδη υπάρχον Λογαριασμός.*  
1. Το σύστημα εμφανίζει κατάλληλο μήνυμα
2. Η ΠΧ τερματίζει

### Β) Επεξεργασία Στοιχείων

1. Ο χρήστης επιλέγει Σύνδεση
   * *1α. Ο χρήστης είναι ήδη συνδεδεμένος*
    1. Η ΠΧ πάει στο βήμα 5 *
2. Το σύστημα εμφανίζει την φόρμα σύνδεσης
3. Ο χρήστης συμπληρώνει τη φόρμα και επιλέγει σύνδεση
   * *3α. Ο χρήστης συμπληρώνει τα στοιχεία λάθος*
    1. Η σύνδεση αποτυγχάνει
    2. Το σύστημα εμφανίζει κατάλληλο μήνυμα σφάλματος
    3. Η ΠΧ επιστρέφει στο βήμα 2 *
4. Το σύστημα ταυτοποιεί το χρήστη και φορτώνει το προφίλ του
5. Ο χρήστης επιλέγει επεξεργασία προφίλ
6. Το σύστημα εμφανίζει τα στοιχεία
7. Ο χρήστης κάνει αλλαγές και επιλέγει αποθήκευση 
   * *7α. Ο χρήστης συμπλήρωσε με λάθος πληροφορίες πεδία ή διέγραψε υποχρεωτικά πεδία*
    1. Το σύστημα εμφανίζει κατάλληλο μήνυμα σφάλματος
    2. Η ΠΧ επιστρέφει στο βήμα 6 

### Γ) Κλείσιμο Λογαριασμού

1. Ο χρήστης επιλέγει Σύνδεση
   * *1α. Ο χρήστης είναι ήδη συνδεδεμένος*
    1. Η ΠΧ πάει στο βήμα 5 *
2. Το σύστημα εμφανίζει την φόρμα σύνδεσης
3. Ο χρήστης συμπληρώνει τη φόρμα και επιλέγει σύνδεση
   * *3α. Ο χρήστης συμπληρώνει τα στοιχεία λάθος*
    1. Η σύνδεση αποτυγχάνει
    2. Το σύστημα εμφανίζει κατάλληλο μήνυμα σφάλματος
    3. Η ΠΧ επιστρέφει στο βήμα 2 *
4. Το σύστημα ταυτοποιεί το χρήστη και φορτώνει το προφίλ του
5. Ο χρήστης επιλέγει επεξεργασία προφίλ
6. Το σύστημα εμφανίζει τα στοιχεία
7. Ο χρήστης επιλέγει Κλείσιμο Λογαριασμού
   * *7α. Το χαρτοφυλάκιο του χρήστη δεν είναι κενό*
    1. Το σύστημα ενημερώνει τον χρήστη ότι δεν μπορεί να κλείσει τον λογαριασμό του αν έχει υπό την κατοχή του μετοχές.