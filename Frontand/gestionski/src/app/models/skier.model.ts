export interface Skier {
  numSkier?: number;
  firstName: string;
  lastName: string;
  dateOfBirth: string;  // Format: "yyyy-mm-dd"
  city: string;
  subscription?: any; // You can create a specific model for Subscription
  pistes?: any[];
  registrations?: any[];
}
