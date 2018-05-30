/********************************************************************************
 * ROBOTEST
 * Copyright (C) 2018 CAST-INFO, S.A. www.cast-info.es
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
import {Component, ViewChild, Input} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource, MatFormField} from '@angular/material';
import { OnInit, AfterViewInit } from '@angular/core/src/metadata/lifecycle_hooks';
@Component({
  selector: 'robotest-mgrid',
  styleUrls: ['mgrid.component.scss'],
  templateUrl: 'mgrid.component.html',
})
export class MaterialGridComponent implements OnInit, AfterViewInit {
  displayedColumns = [];
  dataSource: MatTableDataSource<any>;
  @Input() gridData: any;
  @Input() gridType: string;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor() {
  }

  ngOnInit() {
    if (this.gridType === 'SUITE') {
      this.displayedColumns = ['order', 'name', 'description', 'infos', 'warnings', 'errors', 'durationMillis'];
      this.dataSource = new MatTableDataSource(this.gridData.suites);
    } else if (this.gridType === 'CASE') {
      this.displayedColumns = ['order', 'name', 'description', 'infos', 'warnings', 'errors', 'durationMillis'];
      this.dataSource = new MatTableDataSource(this.gridData.cases);
    } else if (this.gridType === 'STEP') {
      this.displayedColumns = ['order', 'name', 'description', 'status', 'durationMillis'];
      this.dataSource = new MatTableDataSource(this.gridData.steps);
    } else if (this.gridType === 'VALIDATION') {
      this.displayedColumns = ['order', 'status', 'type', 'resource'];
      this.dataSource = new MatTableDataSource(this.gridData.validations);
    }
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string) {
    console.log(filterValue);
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  selectionChange(row): void {
    if (this.gridType === 'SUITE') {
      this.gridData.suiteSelected = row;
    } else if (this.gridType === 'CASE') {
      this.gridData.caseSelected = row;
    } else if (this.gridType === 'STEP') {
      this.gridData.stepSelected = row;
    } else if (this.gridType === 'VALIDATION') {
      this.gridData.validationSelected = row;
    }
  }

  isOkDuration(millis: number): boolean {
    return millis < 1000;
  }

  isWarnDuration(millis: number): boolean {
    return millis >= 1000 && millis < 10000;
  }

  isErrorDuration(millis: number): boolean {
    return millis >= 10000;
  }
}

