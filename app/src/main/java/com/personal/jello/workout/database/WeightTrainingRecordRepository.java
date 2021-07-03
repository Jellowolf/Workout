package com.personal.jello.workout.database;

import android.app.Application;
import android.os.AsyncTask;

import com.personal.jello.workout.dataAccess.WeightTrainingRecordDao;
import com.personal.jello.workout.models.WeightTrainingRecordDetail;
import com.personal.jello.workout.models.WeightTrainingRecordGeneral;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WeightTrainingRecordRepository {
    private WeightTrainingRecordDao weightTrainingRecordDao;

    public WeightTrainingRecordRepository(Application application) {
        WorkoutDatabase db = WorkoutDatabase.getWorkoutDatabase(application);
        weightTrainingRecordDao = db.weightTrainingRecordDao();
    }

    public List<WeightTrainingRecordGeneral> getAllRecords() {
        try {
            return new getAllAsyncTask(weightTrainingRecordDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getAllAsyncTask extends AsyncTask<Void, Void, List<WeightTrainingRecordGeneral>> {
        private WeightTrainingRecordDao weightTrainingAsyncTaskDao;

        getAllAsyncTask(WeightTrainingRecordDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected List<WeightTrainingRecordGeneral> doInBackground(final Void... params) {
            List<WeightTrainingRecordGeneral> records = weightTrainingAsyncTaskDao.getAll();
            return records != null ? weightTrainingAsyncTaskDao.getAll() : null;
        }
    }

    public List<WeightTrainingRecordDetail> getAllDetails() {
        try {
            return new getAllDetailsAsyncTask(weightTrainingRecordDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getAllDetailsAsyncTask extends AsyncTask<Void, Void, List<WeightTrainingRecordDetail>> {
        private WeightTrainingRecordDao weightTrainingAsyncTaskDao;

        getAllDetailsAsyncTask(WeightTrainingRecordDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected List<WeightTrainingRecordDetail> doInBackground(final Void... params) {
            List<WeightTrainingRecordDetail> details = weightTrainingAsyncTaskDao.getAllDetails();
            return details != null ? weightTrainingAsyncTaskDao.getAllDetails() : null;
        }
    }

    public void insertRecord(WeightTrainingRecordGeneral record) {
        new insertAsyncTask(weightTrainingRecordDao).execute(record);
    }

    private static class insertAsyncTask extends AsyncTask<WeightTrainingRecordGeneral, Void, Void> {
        private WeightTrainingRecordDao weightTrainingAsyncTaskDao;

        insertAsyncTask(WeightTrainingRecordDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingRecordGeneral... params) {
            weightTrainingAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insertRecords(WeightTrainingRecordGeneral[] records) {
        new insertsAsyncTask(weightTrainingRecordDao).execute(records);
    }

    private static class insertsAsyncTask extends AsyncTask<WeightTrainingRecordGeneral[], Void, Void> {
        private WeightTrainingRecordDao weightTrainingAsyncTaskDao;

        insertsAsyncTask(WeightTrainingRecordDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingRecordGeneral[]... params) {
            weightTrainingAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void updateRecord(WeightTrainingRecordGeneral record) {
        new updateAsyncTask(weightTrainingRecordDao).execute(record);
    }

    private static class updateAsyncTask extends AsyncTask<WeightTrainingRecordGeneral, Void, Void> {
        private WeightTrainingRecordDao weightTrainingAsyncTaskDao;

        updateAsyncTask(WeightTrainingRecordDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingRecordGeneral... params) {
            weightTrainingAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    public void deleteRecord(WeightTrainingRecordGeneral record) {
        new deleteAsyncTask(weightTrainingRecordDao).execute(record);
    }

    private static class deleteAsyncTask extends AsyncTask<WeightTrainingRecordGeneral, Void, Void> {
        private WeightTrainingRecordDao weightTrainingAsyncTaskDao;

        deleteAsyncTask(WeightTrainingRecordDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingRecordGeneral... params) {
            weightTrainingAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
