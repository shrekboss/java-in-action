package org.crayzer.design.design_mode_principle.inaciton.performanceCounter.v2;

import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.RequestStat;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.v1.EmailSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmailViewer implements StatViewer {
    private EmailSender emailSender;
    private List<String> toAddresses;

    public EmailViewer() {
        this.emailSender = new EmailSender(/*省略参数*/);
        this.toAddresses = new ArrayList<>();
    }

    public EmailViewer(List<String> emailToAddressesr) {
        this.toAddresses = emailToAddressesr;
    }

    public void addToAddress(String address) {
        toAddresses.add(address);
    }

    @Override
    public void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills) {
        // format the requestStats to HTML style.
        // send it to email toAddresses.
    }
}
