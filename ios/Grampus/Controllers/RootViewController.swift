//
//  RootViewController.swift
//  Grampus
//
//  Created by Тарас on 18.11.2019.
//  Copyright © 2019 Тимур Кошевой. All rights reserved.
//

import UIKit

class RootViewController: UIViewController {

    let myRefreshControl: UIRefreshControl = {
        let refreshControl = UIRefreshControl()
        refreshControl.addTarget(self, action: #selector(pullToRefresh(sender:)), for: .valueChanged)
        return refreshControl
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    @objc func pullToRefresh(sender: UIRefreshControl) {
        sender.endRefreshing()
    }
    
}
