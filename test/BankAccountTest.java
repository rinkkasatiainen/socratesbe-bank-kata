import com.sun.corba.se.spi.monitoring.MonitoredAttribute;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.ExcludeCategories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class BankAccountTest {

    private Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account( );
    }

    @org.junit.Test
    public void A_new_account_is_empty() throws Exception {
        assertThat(account.isEmpty(), equalTo( true ));
    }

    @org.junit.Test
    public void When_we_deposit_money_the_account_is_not_empty() throws Exception {
        account.deposit( new Money(1) );
        assertThat(account.isEmpty(), equalTo( false ));
    }

    @Test
    public void We_can_withdraw_from_an_account() throws Exception {
        account.deposit( new Money(10) );
        account.withdraw( new Money(1) );
    }

    @Test
    public void When_we_withdraw_all_our_money_our_account_is_empty() throws Exception {
        account.deposit( new Money(10) );
        account.withdraw( new Money(5) );
        account.withdraw( new Money(5) );

        assertThat( account.isEmpty(), equalTo( true ));
    }

    @Test
    public void An_account_cannot_be_overdrawn() throws Exception {
        Money money = new Money(1);
        try {
            account.withdraw( money );
        } catch (AccountCannotBeOverdrawn e) {
            return;
        }
        fail("Expected AccountCannotBeOverdrawn");
    }

    private class Account {
        private boolean hasDeposited;
        private Money balance;

        public Account() {
            this.balance = new Money(0);
        }

        public boolean isEmpty() {
            return balance.equals( new Money(0) );
        }


        public void withdraw(Money money) {
            if( balance.isLessThan( money ) ){
                throw new AccountCannotBeOverdrawn();
            }
            this.balance = balance.substract(money);
        }

        public void deposit(Money money) {
            balance = balance.add(money);
            hasDeposited = true;
        }
    }

    private class AccountCannotBeOverdrawn extends RuntimeException {
    }

    private class Money {
        private int amount;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Money money = (Money) o;

            return amount == money.amount;
        }

        @Override
        public int hashCode() {
            return amount;
        }

        public Money(int amount) {
            this.amount = amount;
        }

        public Money add(Money other) {
            return new Money(this.amount + other.amount);
        }

        public boolean isLessThan(Money other) {
            return this.amount < other.amount;
        }

        public Money substract(Money other) {
            return new Money(this.amount - other.amount );
        }
    }
}
