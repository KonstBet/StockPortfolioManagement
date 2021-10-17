# INF138 Project Template

Ένα απλό πρότυπο οργάνωσης του κώδικα και της τεχνικής τεκμηρίωσης για τις εξαμηνιαίες εργασίες του μαθήματος Τεχνολογία Λογισμικού ([INF138](https://eclass.aueb.gr/courses/INF138/)) του Τμήματος Πληροφορικής Οικονομικού Πανεπιστημίου Αθηνών.

Η τρέχουσα έκδοση περιλαμβάνει την [προδιαγραφή των απαιτήσεων λογισμικού](src/site/SoftwareRequirementsSpecification.md) με προσαρμογή του `IEEE Std 830-1998` για την ενσωμάτωση απαιτήσεων σε μορφή περιπτώσεων χρήσης. Για περισσότερες λεπτομέρειες μπορείτε να ανατρέξετε στο βιβλίο [Μ Γιακουμάκης, Ν. Διαμαντίδης, Τεχνολογία Λογισμικού, Σταμούλης, 2009](https://www.softeng.gr).

## Χρήσιμες εντολές:

Η διαχείριση της οικοδόμησης του έργου μπορεί να γίνει με μια σειρά βασικών εντολών:
- `mvn`: εκτελεί τον προκαθορισμένο κύκλο οικοδόμησης
- `mvn test`: εκτελεί τα unit tests του project
- `mvn site`: παράγει την τεκμηρίωση του project σε μορφή HTML. Τα παραγόμενα αρχεία 
  είναι διαθέσιμα στην τοποθεσία `target/site/`
- `mvn umlet:convert -Dumlet.targetDir=src/site/markdown/uml`: παράγει αρχεία εικόνας png για όλα τα διαγράμματα που βρίσκονται στην τοποθεσία `src/site/markdown/uml`. Συστήνεται η κλήση της εντολής πριν την υποβολή μιας νέας έκδοσης διαγραμμάτων στο git repository (`git commit`). Ως αποτέλεσμα τα παραγόμενα αρχεία εικόνας των διαγραμμάτων συνοδεύουν τα πηγαία αρχεία έτσι ώστε να είναι εύκολη η πλοήγηση στην τεκμηρίωση του project  μέσω του github.  

