export enum TypeSubscription {
                      ANNUAL = 'ANNUAL',
                      SEMESTRIEL = 'SEMESTRIEL',
                      MONTHLY = 'MONTHLY'
                    }
                    
                    export interface Subscription {
                      numSub?: number;
                      startDate: string;
                      endDate: string;
                      price: number;
                      typeSub: TypeSubscription;
                    }
                    