# A-Simple-Quiz-Application

# Before executing this files need to change the package names as your package.
Like Replace your.package.name with whatever package you want (e.g., com.myquizapp), consistently in all files. Example:

package your.package.name;

# Key features implemented:
10 questions with 4 options each.

Correct answers stored as long indexes.

# Lifelines:

Audience Poll (once): shows simulated vote percentages favoring the correct answer.

50/50 (once): removes two incorrect options (leaving the correct one and one random wrong).

Quit: Player can type Q (or q) before answering to walk away with current guaranteed money.

# Payout rules:

Wrong answer up to question 5 → total winnings = 0.

Wrong answer on question 6 or 7 → winnings revert to what was secured at question 5.

Wrong answer beyond question 7 → winnings revert to what was secured at question 7.

# Colorized console output:

Correct answer message in green.

Wrong answer message in red.

Game completion message in purple.
